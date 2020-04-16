package jolyjdia.connector.packet;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.handler.codec.DecoderException;
import io.netty.handler.codec.EncoderException;
import io.netty.util.ByteProcessor;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.channels.FileChannel;
import java.nio.channels.GatheringByteChannel;
import java.nio.channels.ScatteringByteChannel;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.UUID;

public class PacketBuffer extends ByteBuf {
    private final ByteBuf buf;

    public PacketBuffer(ByteBuf wrapped) {
        buf = wrapped;
    }

    public static int getVarIntSize(int input) {
        for (int i = 1; i < 5; ++i) {
            if ((input & -1 << i * 7) == 0) {
                return i;
            }
        }

        return 5;
    }

    public PacketBuffer writeByteArray(byte @NotNull [] array) {
        this.writeVarInt(array.length);
        this.writeBytes(array);
        return this;
    }

    public byte[] readByteArray() {
        return this.readByteArray(this.readableBytes());
    }

    public byte[] readByteArray(int maxLength) {
        int i = this.readVarInt();

        if (i > maxLength) {
            throw new DecoderException("ByteArray with size " + i + " is bigger than allowed " + maxLength);
        } else {
            byte[] abyte = new byte[i];
            this.readBytes(abyte);
            return abyte;
        }
    }

    public PacketBuffer writeVarIntArray(int @NotNull [] array) {
        this.writeVarInt(array.length);

        for (int i : array) {
            this.writeVarInt(i);
        }

        return this;
    }

    public int[] readVarIntArray() {
        return this.readVarIntArray(this.readableBytes());
    }

    public int[] readVarIntArray(int maxLength) {
        int i = this.readVarInt();

        if (i > maxLength) {
            throw new DecoderException("VarIntArray with size " + i + " is bigger than allowed " + maxLength);
        } else {
            int[] aint = new int[i];

            for (int j = 0; j < i; ++j) {
                aint[j] = this.readVarInt();
            }

            return aint;
        }
    }

    public PacketBuffer writeLongArray(long @NotNull [] array) {
        this.writeVarInt(array.length);

        for (long i : array) {
            this.writeLong(i);
        }

        return this;
    }

    public <T extends Enum<T>> T readEnumValue(@NotNull Class<T> enumClass) {
        return enumClass.getEnumConstants()[this.readVarInt()];
    }

    public PacketBuffer writeEnumValue(@NotNull Enum<?> value) {
        return this.writeVarInt(value.ordinal());
    }

    public int readVarInt() {
        int i = 0;
        int j = 0;

        while (true) {
            byte b0 = this.readByte();
            i |= (b0 & 127) << j++ * 7;

            if (j > 5) {
                throw new RuntimeException("VarInt too big");
            }

            if ((b0 & 128) != 128) {
                break;
            }
        }

        return i;
    }

    public long readVarLong() {
        long i = 0L;
        int j = 0;

        while (true) {
            byte b0 = this.readByte();
            i |= (long) (b0 & 127) << j++ * 7;

            if (j > 10) {
                throw new RuntimeException("VarLong too big");
            }

            if ((b0 & 128) != 128) {
                break;
            }
        }

        return i;
    }

    public void writeUniqueId(@NotNull UUID uuid) {
        this.writeLong(uuid.getMostSignificantBits());
        this.writeLong(uuid.getLeastSignificantBits());
    }

    public UUID readUniqueId() {
        return new UUID(this.readLong(), this.readLong());
    }

    public PacketBuffer writeVarInt(int input) {
        while ((input & -128) != 0) {
            this.writeByte(input & 127 | 128);
            input >>>= 7;
        }

        this.writeByte(input);
        return this;
    }

    public PacketBuffer writeVarLong(long value) {
        while ((value & -128L) != 0L) {
            this.writeByte((int) (value & 127L) | 128);
            value >>>= 7;
        }

        this.writeByte((int) value);
        return this;
    }

    public String readString(int maxLength) {
        int i = this.readVarInt();

        int max = maxLength << 2;
        if (i > max) {
            throw new DecoderException(
                    "The received encoded string buffer length is longer than maximum allowed (" + i + " > " + max + ')'
            );
        } else if (i < 0) {
            throw new DecoderException(
                    "The received encoded string buffer length is less than zero! Weird string!"
            );
        } else {
            String s = this.toString(this.readerIndex(), i, StandardCharsets.UTF_8);
            this.readerIndex(this.readerIndex() + i);

            if (s.length() > maxLength) {
                throw new DecoderException(
                        "The received string length is longer than maximum allowed (" + i + " > " + maxLength + ')'
                );
            } else {
                return s;
            }
        }
    }

    public void writeString(@NotNull String s) {
        byte[] abyte = s.getBytes(StandardCharsets.UTF_8);

        if (abyte.length > 32767) {
            throw new EncoderException(
                    "String too big (was " + abyte.length + " bytes encoded, max " + 32767 + ')'
            );
        } else {
            this.writeVarInt(abyte.length);
            this.writeBytes(abyte);
        }
    }

    /**
     * Override method
     */
    @Override
    public int capacity() {
        return buf.capacity();
    }

    @Override
    public ByteBuf capacity(int pCapacity1) {
        return buf.capacity(pCapacity1);
    }

    @Override
    public int maxCapacity() {
        return buf.maxCapacity();
    }

    @Override
    public ByteBufAllocator alloc() {
        return buf.alloc();
    }

    @Override
    @Deprecated
    public ByteOrder order() {
        return buf.order();
    }

    @Override
    @Deprecated
    public ByteBuf order(ByteOrder order) {
        return buf.order(order);
    }

    @Override
    public ByteBuf unwrap() {
        return buf.unwrap();
    }

    @Override
    public boolean isDirect() {
        return buf.isDirect();
    }

    @Override
    public boolean isReadOnly() {
        return buf.isReadOnly();
    }

    @Override
    public ByteBuf asReadOnly() {
        return buf.asReadOnly();
    }

    @Override
    public int readerIndex() {
        return buf.readerIndex();
    }

    @Override
    public ByteBuf readerIndex(int pReaderIndex1) {
        return buf.readerIndex(pReaderIndex1);
    }

    @Override
    public int writerIndex() {
        return buf.writerIndex();
    }

    @Override
    public ByteBuf writerIndex(int pWriterIndex1) {
        return buf.writerIndex(pWriterIndex1);
    }

    @Override
    public ByteBuf setIndex(int i, int i1) {
        return buf.setIndex(i, i1);
    }

    @Override
    public int readableBytes() {
        return buf.readableBytes();
    }

    @Override
    public int writableBytes() {
        return buf.writableBytes();
    }

    @Override
    public int maxWritableBytes() {
        return buf.maxWritableBytes();
    }

    @Override
    public boolean isReadable() {
        return buf.isReadable();
    }

    @Override
    public boolean isReadable(int i) {
        return buf.isReadable(i);
    }

    @Override
    public boolean isWritable() {
        return buf.isWritable();
    }

    @Override
    public boolean isWritable(int i) {
        return buf.isWritable(i);
    }

    @Override
    public ByteBuf clear() {
        return buf.clear();
    }

    @Override
    public ByteBuf markReaderIndex() {
        return buf.markReaderIndex();
    }

    @Override
    public ByteBuf resetReaderIndex() {
        return buf.resetReaderIndex();
    }

    @Override
    public ByteBuf markWriterIndex() {
        return buf.markWriterIndex();
    }

    @Override
    public ByteBuf resetWriterIndex() {
        return buf.resetWriterIndex();
    }

    @Override
    public ByteBuf discardReadBytes() {
        return buf.discardReadBytes();
    }

    @Override
    public ByteBuf discardSomeReadBytes() {
        return buf.discardSomeReadBytes();
    }

    @Override
    public ByteBuf ensureWritable(int i) {
        return buf.ensureWritable(i);
    }

    @Override
    public int ensureWritable(int i, boolean b) {
        return buf.ensureWritable(i, b);
    }

    @Override
    public boolean getBoolean(int i) {
        return buf.getBoolean(i);
    }

    @Override
    public byte getByte(int i) {
        return buf.getByte(i);
    }

    @Override
    public short getUnsignedByte(int i) {
        return buf.getUnsignedByte(i);
    }

    @Override
    public short getShort(int i) {
        return buf.getShort(i);
    }

    @Override
    public short getShortLE(int i) {
        return buf.getShortLE(i);
    }

    @Override
    public int getUnsignedShort(int i) {
        return buf.getUnsignedShort(i);
    }

    @Override
    public int getUnsignedShortLE(int i) {
        return buf.getUnsignedShortLE(i);
    }

    @Override
    public int getMedium(int i) {
        return buf.getMedium(i);
    }

    @Override
    public int getMediumLE(int i) {
        return buf.getMediumLE(i);
    }

    @Override
    public int getUnsignedMedium(int i) {
        return buf.getUnsignedMedium(i);
    }

    @Override
    public int getUnsignedMediumLE(int i) {
        return buf.getUnsignedMediumLE(i);
    }

    @Override
    public int getInt(int i) {
        return buf.getInt(i);
    }

    @Override
    public int getIntLE(int i) {
        return buf.getIntLE(i);
    }

    @Override
    public long getUnsignedInt(int i) {
        return buf.getUnsignedInt(i);
    }

    @Override
    public long getUnsignedIntLE(int i) {
        return buf.getUnsignedIntLE(i);
    }

    @Override
    public long getLong(int i) {
        return buf.getLong(i);
    }

    @Override
    public long getLongLE(int i) {
        return buf.getLongLE(i);
    }

    @Override
    public char getChar(int i) {
        return buf.getChar(i);
    }

    @Override
    public float getFloat(int i) {
        return buf.getFloat(i);
    }

    @Override
    public double getDouble(int i) {
        return buf.getDouble(i);
    }

    @Override
    public ByteBuf getBytes(int i, ByteBuf byteBuf) {
        return buf.getBytes(i, byteBuf);
    }

    @Override
    public ByteBuf getBytes(int i, ByteBuf byteBuf, int i1) {
        return buf.getBytes(i, byteBuf, i1);
    }

    @Override
    public ByteBuf getBytes(int i, ByteBuf byteBuf, int i1, int i2) {
        return buf.getBytes(i, byteBuf, i1, i2);
    }

    @Override
    public ByteBuf getBytes(int i, byte[] bytes) {
        return buf.getBytes(i, bytes);
    }

    @Override
    public ByteBuf getBytes(int i, byte[] bytes, int i1, int i2) {
        return buf.getBytes(i, bytes, i1, i2);
    }

    @Override
    public ByteBuf getBytes(int pGetBytes1, ByteBuffer byteBuffer) {
        return buf.getBytes(pGetBytes1, byteBuffer);
    }

    @Override
    public ByteBuf getBytes(int i, OutputStream outputStream, int i1) throws IOException {
        return buf.getBytes(i, outputStream, i1);
    }

    @Override
    public int getBytes(int i, GatheringByteChannel byteChannel, int i1) throws IOException {
        return buf.getBytes(i, byteChannel, i1);
    }

    @Override
    public int getBytes(int i, FileChannel fileChannel, long l, int i1)
            throws IOException {
        return buf.getBytes(i, fileChannel, l, i1);
    }

    @Override
    public CharSequence getCharSequence(int i, int i1, Charset charset) {
        return buf.getCharSequence(i, i1, charset);
    }

    @Override
    public ByteBuf setBoolean(int i, boolean b) {
        return buf.setBoolean(i, b);
    }

    @Override
    public ByteBuf setByte(int i, int i1) {
        return buf.setByte(i, i1);
    }

    @Override
    public ByteBuf setShort(int i, int i1) {
        return buf.setShort(i, i1);
    }

    @Override
    public ByteBuf setShortLE(int i, int i1) {
        return buf.setShortLE(i, i1);
    }

    @Override
    public ByteBuf setMedium(int i, int i1) {
        return buf.setMedium(i, i1);
    }

    @Override
    public ByteBuf setMediumLE(int i, int i1) {
        return buf.setMediumLE(i, i1);
    }

    @Override
    public ByteBuf setInt(int i, int i1) {
        return buf.setInt(i, i1);
    }

    @Override
    public ByteBuf setIntLE(int i, int i1) {
        return buf.setIntLE(i, i1);
    }

    @Override
    public ByteBuf setLong(int i, long l) {
        return buf.setLong(i, l);
    }

    @Override
    public ByteBuf setLongLE(int i, long l) {
        return buf.setLongLE(i, l);
    }

    @Override
    public ByteBuf setChar(int i, int i1) {
        return buf.setChar(i, i1);
    }

    @Override
    public ByteBuf setFloat(int i, float v) {
        return buf.setFloat(i, v);
    }

    @Override
    public ByteBuf setDouble(int i, double v) {
        return buf.setDouble(i, v);
    }

    @Override
    public ByteBuf setBytes(int i, ByteBuf byteBuf) {
        return buf.setBytes(i, byteBuf);
    }

    @Override
    public ByteBuf setBytes(int i, ByteBuf byteBuf, int i1) {
        return buf.setBytes(i, byteBuf, i1);
    }

    @Override
    public ByteBuf setBytes(int i, ByteBuf byteBuf, int i1, int i2) {
        return buf.setBytes(i, byteBuf, i1, i2);
    }

    @Override
    public ByteBuf setBytes(int i, byte[] bytes) {
        return buf.setBytes(i, bytes);
    }

    @Override
    public ByteBuf setBytes(int i, byte[] bytes, int i1, int i2) {
        return buf.setBytes(i, bytes, i1, i2);
    }

    @Override
    public ByteBuf setBytes(int i, ByteBuffer byteBuffer) {
        return buf.setBytes(i, byteBuffer);
    }

    @Override
    public int setBytes(int i, InputStream inputStream, int i1) throws IOException {
        return buf.setBytes(i, inputStream, i1);
    }

    @Override
    public int setBytes(int i, ScatteringByteChannel byteChannel, int i1) throws IOException {
        return buf.setBytes(i, byteChannel, i1);
    }

    @Override
    public int setBytes(int i, FileChannel fileChannel, long l, int i1) throws IOException {
        return buf.setBytes(i, fileChannel, l, i1);
    }

    @Override
    public ByteBuf setZero(int i, int i1) {
        return buf.setZero(i, i1);
    }

    @Override
    public int setCharSequence(int i, CharSequence sequence, Charset charset) {
        return buf.setCharSequence(i, sequence, charset);
    }

    @Override
    public boolean readBoolean() {
        return buf.readBoolean();
    }

    @Override
    public byte readByte() {
        return buf.readByte();
    }

    @Override
    public short readUnsignedByte() {
        return buf.readUnsignedByte();
    }

    @Override
    public short readShort() {
        return buf.readShort();
    }

    @Override
    public short readShortLE() {
        return buf.readShortLE();
    }

    @Override
    public int readUnsignedShort() {
        return buf.readUnsignedShort();
    }

    @Override
    public int readUnsignedShortLE() {
        return buf.readUnsignedShortLE();
    }

    @Override
    public int readMedium() {
        return buf.readMedium();
    }

    @Override
    public int readMediumLE() {
        return buf.readMediumLE();
    }

    @Override
    public int readUnsignedMedium() {
        return buf.readUnsignedMedium();
    }

    @Override
    public int readUnsignedMediumLE() {
        return buf.readUnsignedMediumLE();
    }

    @Override
    public int readInt() {
        return buf.readInt();
    }

    @Override
    public int readIntLE() {
        return buf.readIntLE();
    }

    @Override
    public long readUnsignedInt() {
        return buf.readUnsignedInt();
    }

    @Override
    public long readUnsignedIntLE() {
        return buf.readUnsignedIntLE();
    }

    @Override
    public long readLong() {
        return buf.readLong();
    }

    @Override
    public long readLongLE() {
        return buf.readLongLE();
    }

    @Override
    public char readChar() {
        return buf.readChar();
    }

    @Override
    public float readFloat() {
        return buf.readFloat();
    }

    @Override
    public double readDouble() {
        return buf.readDouble();
    }

    @Override
    public ByteBuf readBytes(int i) {
        return buf.readBytes(i);
    }

    @Override
    public ByteBuf readSlice(int i) {
        return buf.readSlice(i);
    }

    @Override
    public ByteBuf readRetainedSlice(int i) {
        return buf.readRetainedSlice(i);
    }

    @Override
    public ByteBuf readBytes(ByteBuf byteBuf) {
        return buf.readBytes(byteBuf);
    }

    @Override
    public ByteBuf readBytes(ByteBuf byteBuf, int i) {
        return buf.readBytes(byteBuf, i);
    }

    @Override
    public ByteBuf readBytes(ByteBuf byteBuf, int i, int i1) {
        return buf.readBytes(byteBuf, i, i1);
    }

    @Override
    public ByteBuf readBytes(byte[] pReadBytes1) {
        return buf.readBytes(pReadBytes1);
    }

    @Override
    public ByteBuf readBytes(byte[] bytes, int i, int i1) {
        return buf.readBytes(bytes, i, i1);
    }

    @Override
    public ByteBuf readBytes(ByteBuffer byteBuffer) {
        return buf.readBytes(byteBuffer);
    }

    @Override
    public ByteBuf readBytes(OutputStream outputStream, int i) throws IOException {
        return buf.readBytes(outputStream, i);
    }

    @Override
    public int readBytes(GatheringByteChannel byteChannel, int i) throws IOException {
        return buf.readBytes(byteChannel, i);
    }

    @Override
    public CharSequence readCharSequence(int i, Charset charset) {
        return buf.readCharSequence(i, charset);
    }

    @Override
    public int readBytes(FileChannel fileChannel, long l, int i) throws IOException {
        return buf.readBytes(fileChannel, l, i);
    }

    @Override
    public ByteBuf skipBytes(int i) {
        return buf.skipBytes(i);
    }

    @Override
    public ByteBuf writeBoolean(boolean b) {
        return buf.writeBoolean(b);
    }

    @Override
    public ByteBuf writeByte(int i) {
        return buf.writeByte(i);
    }

    @Override
    public ByteBuf writeShort(int i) {
        return buf.writeShort(i);
    }

    @Override
    public ByteBuf writeShortLE(int i) {
        return buf.writeShortLE(i);
    }

    @Override
    public ByteBuf writeMedium(int i) {
        return buf.writeMedium(i);
    }

    @Override
    public ByteBuf writeMediumLE(int i) {
        return buf.writeMediumLE(i);
    }

    @Override
    public ByteBuf writeInt(int i) {
        return buf.writeInt(i);
    }

    @Override
    public ByteBuf writeIntLE(int i) {
        return buf.writeIntLE(i);
    }

    @Override
    public ByteBuf writeLong(long l) {
        return buf.writeLong(l);
    }

    @Override
    public ByteBuf writeLongLE(long l) {
        return buf.writeLongLE(l);
    }

    @Override
    public ByteBuf writeChar(int i) {
        return buf.writeChar(i);
    }

    @Override
    public ByteBuf writeFloat(float v) {
        return buf.writeFloat(v);
    }

    @Override
    public ByteBuf writeDouble(double v) {
        return buf.writeDouble(v);
    }

    @Override
    public ByteBuf writeBytes(ByteBuf byteBuf) {
        return buf.writeBytes(byteBuf);
    }

    @Override
    public ByteBuf writeBytes(ByteBuf byteBuf, int i) {
        return buf.writeBytes(byteBuf, i);
    }

    @Override
    public ByteBuf writeBytes(ByteBuf byteBuf, int i, int i1) {
        return buf.writeBytes(byteBuf, i, i1);
    }

    @Override
    public ByteBuf writeBytes(byte[] bytes) {
        return buf.writeBytes(bytes);
    }

    @Override
    public ByteBuf writeBytes(byte[] bytes, int i, int i1) {
        return buf.writeBytes(bytes, i, i1);
    }

    @Override
    public ByteBuf writeBytes(ByteBuffer buffer) {
        return buf.writeBytes(buffer);
    }

    @Override
    public int writeBytes(InputStream inputStream, int pWriteBytes2) throws IOException {
        return buf.writeBytes(inputStream, pWriteBytes2);
    }

    @Override
    public int writeBytes(ScatteringByteChannel byteChannel, int i) throws IOException {
        return buf.writeBytes(byteChannel, i);
    }

    @Override
    public int writeBytes(FileChannel fileChannel, long l, int i) throws IOException {
        return buf.writeBytes(fileChannel, l, i);
    }

    @Override
    public ByteBuf writeZero(int i) {
        return buf.writeZero(i);
    }

    @Override
    public int writeCharSequence(CharSequence sequence, Charset charset) {
        return buf.writeCharSequence(sequence, charset);
    }

    @Override
    public int indexOf(int i, int i1, byte b) {
        return buf.indexOf(i, i1, b);
    }

    @Override
    public int bytesBefore(byte b) {
        return buf.bytesBefore(b);
    }

    @Override
    public int bytesBefore(int i, byte b) {
        return buf.bytesBefore(i, b);
    }

    @Override
    public int bytesBefore(int i, int i1, byte b) {
        return buf.bytesBefore(i, i1, b);
    }

    @Override
    public int forEachByte(ByteProcessor bp) {
        return buf.forEachByte(bp);
    }

    @Override
    public int forEachByte(int i, int i1, ByteProcessor bp) {
        return buf.forEachByte(i, i1, bp);
    }

    @Override
    public int forEachByteDesc(ByteProcessor bp) {
        return buf.forEachByteDesc(bp);
    }

    @Override
    public int forEachByteDesc(int i, int i1, ByteProcessor bp) {
        return buf.forEachByteDesc(i, i1, bp);
    }

    @Override
    public ByteBuf copy() {
        return buf.copy();
    }

    @Override
    public ByteBuf copy(int i, int i1) {
        return buf.copy(i, i1);
    }

    @Override
    public ByteBuf slice() {
        return buf.slice();
    }

    @Override
    public ByteBuf retainedSlice() {
        return buf.retainedSlice();
    }

    @Override
    public ByteBuf slice(int i, int i1) {
        return buf.slice(i, i1);
    }

    @Override
    public ByteBuf retainedSlice(int i, int i1) {
        return buf.retainedSlice(i, i1);
    }

    @Override
    public ByteBuf duplicate() {
        return buf.duplicate();
    }

    @Override
    public ByteBuf retainedDuplicate() {
        return buf.retainedDuplicate();
    }

    @Override
    public int nioBufferCount() {
        return buf.nioBufferCount();
    }

    @Override
    public ByteBuffer nioBuffer() {
        return buf.nioBuffer();
    }

    @Override
    public ByteBuffer nioBuffer(int i, int i1) {
        return buf.nioBuffer(i, i1);
    }

    @Override
    public ByteBuffer internalNioBuffer(int i, int i1) {
        return buf.internalNioBuffer(i, i1);
    }

    @Override
    public ByteBuffer[] nioBuffers() {
        return buf.nioBuffers();
    }

    @Override
    public ByteBuffer[] nioBuffers(int i, int i1) {
        return buf.nioBuffers(i, i1);
    }

    @Override
    public boolean hasArray() {
        return buf.hasArray();
    }

    @Override
    public byte[] array() {
        return buf.array();
    }

    @Override
    public int arrayOffset() {
        return buf.arrayOffset();
    }

    @Override
    public boolean hasMemoryAddress() {
        return buf.hasMemoryAddress();
    }

    @Override
    public long memoryAddress() {
        return buf.memoryAddress();
    }

    @Override
    public String toString(Charset charset) {
        return buf.toString(charset);
    }

    @Override
    public String toString(int i, int i1, Charset charset) {
        return buf.toString(i, i1, charset);
    }

    @Override
    public int hashCode() {
        return buf.hashCode();
    }

    @Override
    public boolean equals(Object o) {
        return buf.equals(o);
    }

    @Override
    public int compareTo(ByteBuf byteBuf) {
        return buf.compareTo(byteBuf);
    }

    @Override
    public String toString() {
        return buf.toString();
    }

    @Override
    public ByteBuf retain(int i) {
        return buf.retain(i);
    }

    @Override
    public ByteBuf retain() {
        return buf.retain();
    }

    @Override
    public ByteBuf touch() {
        return buf.touch();
    }

    @Override
    public ByteBuf touch(Object o) {
        return buf.touch(o);
    }

    @Override
    public int refCnt() {
        return buf.refCnt();
    }

    @Override
    public boolean release() {
        return buf.release();
    }

    @Override
    public boolean release(int i) {
        return buf.release(i);
    }
}