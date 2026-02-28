package io.github.sypiece.opencl;

import org.jocl.*;

import static org.jocl.CL.*;

public class OpenCLMemory extends OpenCLObject<cl_mem> implements AutoCloseable {
    public static OpenCLMemory createBuffer(OpenCLContext context, Flags flags, long size) {
        return new OpenCLMemory(clCreateBuffer(context.context, flags.value, size, null, null));
    }

    public static Byte createByteBuffer(OpenCLContext context, Flags flags, long length) {
        return new Byte(clCreateBuffer(context.context, flags.value, Sizeof.cl_char * length, null, null));
    }

    public static Byte createByteBuffer(OpenCLContext context, Flags flags, byte[] values) {
        return new Byte(clCreateBuffer(context.context, flags.value, (long) Sizeof.cl_char * values.length, Pointer.to(values), null));
    }

    public static Short createShortBuffer(OpenCLContext context, Flags flags, long length) {
        return new Short(clCreateBuffer(context.context, flags.value, Sizeof.cl_short * length, null, null));
    }

    public static Short createShortBuffer(OpenCLContext context, Flags flags, short[] values) {
        return new Short(clCreateBuffer(context.context, flags.value, (long) Sizeof.cl_short * values.length, Pointer.to(values), null));
    }

    public static Integer createIntegerBuffer(OpenCLContext context, Flags flags, long length) {
        return new Integer(clCreateBuffer(context.context, flags.value, Sizeof.cl_int * length, null, null));
    }

    public static Integer createIntegerBuffer(OpenCLContext context, Flags flags, int[] values) {
        return new Integer(clCreateBuffer(context.context, flags.value, (long) Sizeof.cl_int * values.length, Pointer.to(values), null));
    }

    public static Long createLongBuffer(OpenCLContext context, Flags flags, long length) {
        return new Long(clCreateBuffer(context.context, flags.value, Sizeof.cl_long * length, null, null));
    }

    public static Long createLongBuffer(OpenCLContext context, Flags flags, long[] values) {
        return new Long(clCreateBuffer(context.context, flags.value, (long) Sizeof.cl_long * values.length, Pointer.to(values), null));
    }

    public static Float createFloatBuffer(OpenCLContext context, Flags flags, long length) {
        return new Float(clCreateBuffer(context.context, flags.value, Sizeof.cl_float * length, null, null));
    }

    public static Float createFloatBuffer(OpenCLContext context, Flags flags, float[] values) {
        return new Float(clCreateBuffer(context.context, flags.value, (long) Sizeof.cl_float * values.length, Pointer.to(values), null));
    }

    public static Double createDoubleBuffer(OpenCLContext context, Flags flags, long length) {
        return new Double(clCreateBuffer(context.context, flags.value, Sizeof.cl_double * length, null, null));
    }

    public static Double createDoubleBuffer(OpenCLContext context, Flags flags, double[] values) {
        return new Double(clCreateBuffer(context.context, flags.value, (long) Sizeof.cl_double * values.length, Pointer.to(values), null));
    }

    public static OpenCLMemory createSubBuffer(OpenCLMemory parent, Flags flags, long offset, long size) {
        return new OpenCLMemory(clCreateSubBuffer(parent.memory, flags.value, CL_BUFFER_CREATE_TYPE_REGION, new cl_buffer_region(offset, size), null));
    }

    public static Byte createSubByteBuffer(OpenCLMemory parent, Flags flags, long offset, long length) {
        return new Byte(clCreateSubBuffer(parent.memory, flags.value, CL_BUFFER_CREATE_TYPE_REGION, new cl_buffer_region(offset, Sizeof.cl_char * length), null));
    }

    public static Short createSubShortBuffer(OpenCLMemory parent, Flags flags, long offset, long length) {
        return new Short(clCreateSubBuffer(parent.memory, flags.value, CL_BUFFER_CREATE_TYPE_REGION, new cl_buffer_region(offset, Sizeof.cl_short * length), null));
    }

    public static Integer createSubIntegerBuffer(OpenCLMemory parent, Flags flags, long offset, long length) {
        return new Integer(clCreateSubBuffer(parent.memory, flags.value, CL_BUFFER_CREATE_TYPE_REGION, new cl_buffer_region(offset, Sizeof.cl_int * length), null));
    }

    public static Long createSubLongBuffer(OpenCLMemory parent, Flags flags, long offset, long length) {
        return new Long(clCreateSubBuffer(parent.memory, flags.value, CL_BUFFER_CREATE_TYPE_REGION, new cl_buffer_region(offset, Sizeof.cl_long * length), null));
    }

    public static Float createSubFloatBuffer(OpenCLMemory parent, Flags flags, long offset, long length) {
        return new Float(clCreateSubBuffer(parent.memory, flags.value, CL_BUFFER_CREATE_TYPE_REGION, new cl_buffer_region(offset, Sizeof.cl_float * length), null));
    }

    public static Double createSubDoubleBuffer(OpenCLMemory parent, Flags flags, long offset, long length) {
        return new Double(clCreateSubBuffer(parent.memory, flags.value, CL_BUFFER_CREATE_TYPE_REGION, new cl_buffer_region(offset, Sizeof.cl_double * length), null));
    }

    final cl_mem memory;

    OpenCLMemory(cl_mem memory) {
        super(memory, CL::clGetMemObjectInfo);
        this.memory = memory;
    }

    public Type getType() {
        return Type.valueOf(getIntInfo(CL_MEM_TYPE));
    }

    public Flags getFlags() {
        return new Flags(getLongInfo(CL_MEM_FLAGS));
    }

    public long getSize() {
        return getSizeTInfo(CL_MEM_SIZE);
    }

    public int getMapCount() {
        return getIntInfo(CL_MEM_MAP_COUNT);
    }

    public int getReferenceCount() {
        return getIntInfo(CL_MEM_REFERENCE_COUNT);
    }

    public OpenCLContext getContext() {
        return getContextInfo(CL_MEM_CONTEXT);
    }

    public OpenCLMemory getAssociatedMemObject() {
        return getMemoryInfo(CL_MEM_ASSOCIATED_MEMOBJECT);
    }

    public long getOffset() {
        return getSizeTInfo(CL_MEM_OFFSET);
    }

    public Byte toByteBuffer() {
        return new Byte(memory);
    }

    public Short toShortBuffer() {
        return new Short(memory);
    }

    public Integer toIntegerBuffer() {
        return new Integer(memory);
    }

    public Long toLongBuffer() {
        return new Long(memory);
    }

    public Float toFloatBuffer() {
        return new Float(memory);
    }

    public Double toDoubleBuffer() {
        return new Double(memory);
    }

    @Override
    public void close() {
        clReleaseMemObject(memory);
    }

    public enum Type {
        BUFFER(CL_MEM_OBJECT_BUFFER),
        IMAGE2D(CL_MEM_OBJECT_IMAGE2D),
        IMAGE3D(CL_MEM_OBJECT_IMAGE3D);

        public final int value;

        Type(int value) {
            this.value = value;
        }

        public static Type valueOf(int value) {
            for (Type type : values()) {
                if (type.value == value) {
                    return type;
                }
            }
            throw new IllegalArgumentException("Unknown OpenCLMemory.Type value: " + value);
        }
    }

    public static class Flags {
        public static final long
                READ_WRITE = CL_MEM_READ_WRITE,
                WRITE_ONLY = CL_MEM_WRITE_ONLY,
                READ_ONLY = CL_MEM_READ_ONLY,
                USE_HOST_PTR = CL_MEM_USE_HOST_PTR,
                ALLOC_HOST_PTR = CL_MEM_ALLOC_HOST_PTR,
                COPY_HOST_PTR = CL_MEM_COPY_HOST_PTR;

        public long value;

        public Flags() {
            this(0);
        }

        public Flags(long value) {
            this.value = value;
        }

        public boolean isReadWrite() {
            return (value & READ_WRITE) != 0;
        }

        public boolean isWriteOnly() {
            return (value & WRITE_ONLY) != 0;
        }

        public boolean isReadOnly() {
            return (value & READ_ONLY) != 0;
        }

        public boolean isUseHostPtr() {
            return (value & USE_HOST_PTR) != 0;
        }

        public boolean isAllocHostPtr() {
            return (value & ALLOC_HOST_PTR) != 0;
        }

        public boolean isCopyHostPtr() {
            return (value & COPY_HOST_PTR) != 0;
        }

        public boolean is(long flag) {
            return (value & flag) != 0;
        }

        public Flags setReadWrite() {
            value |= READ_WRITE;
            return this;
        }

        public Flags unsetReadWrite() {
            value &= ~READ_WRITE;
            return this;
        }

        public Flags setWriteOnly() {
            value |= WRITE_ONLY;
            return this;
        }

        public Flags unsetWriteOnly() {
            value &= ~WRITE_ONLY;
            return this;
        }

        public Flags setReadOnly() {
            value |= READ_ONLY;
            return this;
        }

        public Flags unsetReadOnly() {
            value &= ~READ_ONLY;
            return this;
        }

        public Flags setUseHostPtr() {
            value |= USE_HOST_PTR;
            return this;
        }

        public Flags unsetUseHostPtr() {
            value &= ~USE_HOST_PTR;
            return this;
        }

        public Flags setAllocHostPtr() {
            value |= ALLOC_HOST_PTR;
            return this;
        }

        public Flags unsetAllocHostPtr() {
            value &= ~ALLOC_HOST_PTR;
            return this;
        }

        public Flags setCopyHostPtr() {
            value |= COPY_HOST_PTR;
            return this;
        }

        public Flags unsetCopyHostPtr() {
            value &= ~COPY_HOST_PTR;
            return this;
        }

        public Flags set(long flag) {
            value |= flag;
            return this;
        }

        public Flags unset(long flag) {
            value &= ~flag;
            return this;
        }
    }

    public static class Byte extends OpenCLMemory {
        Byte(cl_mem memory) {
            super(memory);
        }
    }

    public static class Short extends OpenCLMemory {
        Short(cl_mem memory) {
            super(memory);
        }
    }

    public static class Integer extends OpenCLMemory {
        Integer(cl_mem memory) {
            super(memory);
        }
    }

    public static class Long extends OpenCLMemory {
        Long(cl_mem memory) {
            super(memory);
        }
    }

    public static class Float extends OpenCLMemory {
        Float(cl_mem memory) {
            super(memory);
        }
    }

    public static class Double extends OpenCLMemory {
        Double(cl_mem memory) {
            super(memory);
        }
    }
}
