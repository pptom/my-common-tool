package com.sinotrans.gd.sscsi.common.service.serializer;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.ByteBufferInput;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import com.esotericsoftware.kryo.serializers.JavaSerializer;
import com.esotericsoftware.kryo.util.Pool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

/**
 * @author tangzhijie
 * @since 2021-05-23 20:05
 */
public class KryoRedisSerializer<T> implements RedisSerializer<T> {

    private static final Logger log = LoggerFactory.getLogger(KryoRedisSerializer.class);

    private static final byte[] EMPTY_BYTE_ARRAY = new byte[0];

    private static final Pool<Kryo> kryoPool = new Pool<Kryo>(true, false, 512) {

        @Override
        protected Kryo create() {
            Kryo kryo = new Kryo();
            kryo.setRegistrationRequired(false);
            kryo.setReferences(false);
            kryo.addDefaultSerializer(Throwable.class, new JavaSerializer());
            return kryo;
        }
    };
    private static final Pool<Output> outputPool = new Pool<Output>(true, false, 512) {
        @Override
        protected Output create() {
            return new Output(8192, -1);
        }
    };
    private static final Pool<Input> inputPool = new Pool<Input>(true, false, 512) {
        @Override
        protected Input create() {
            return new ByteBufferInput(8192);
        }
    };
    private Class<T> clazz;

    public KryoRedisSerializer(Class<T> clazz) {
        super();
        this.clazz = clazz;
    }

    @Override
    public byte[] serialize(T t) throws SerializationException {
        if (null == t) {
            return EMPTY_BYTE_ARRAY;
        }
        Kryo kryo = null;
        Output output = null;
        try {
            output = outputPool.obtain();
            kryo = kryoPool.obtain();
            kryo.writeClassAndObject(output, t);
            output.flush();
            return output.toBytes();
        } finally {
            if (output != null) {
                outputPool.free(output);
            }
            if (kryo != null) {
                kryoPool.free(kryo);
            }
        }
    }

    @Override
    public T deserialize(byte[] bytes) throws SerializationException {
        if (null == bytes || bytes.length <= 0) {
            return null;
        }
        Kryo kryo = null;
        Input input = null;
        try {
            input = inputPool.obtain();
            input.setInputStream(new ByteArrayInputStream(bytes));
            kryo = kryoPool.obtain();
            @SuppressWarnings("unchecked")
            T t = (T) kryo.readClassAndObject(input);
            return t;
        } finally {
            if (input != null) {
                inputPool.free(input);
            }
            if (kryo != null) {
                kryoPool.free(kryo);
            }
        }
    }
}
