package io.github.sypiece.opencl;

import org.jocl.Sizeof;
import org.jocl.cl_buffer_region;
import org.jocl.cl_mem;

import static org.jocl.CL.CL_BUFFER_CREATE_TYPE_REGION;
import static org.jocl.CL.clCreateSubBuffer;

public class OpenCLSubMemory extends OpenCLMemory {
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

    OpenCLSubMemory(cl_mem memory) {
        super(memory);
    }

    public Byte toSubByteBuffer() {
        return new Byte(memory);
    }

    public Short toSubShortBuffer() {
        return new Short(memory);
    }

    public Integer toSubIntegerBuffer() {
        return new Integer(memory);
    }

    public Long toSubLongBuffer() {
        return new Long(memory);
    }

    public Float toSubFloatBuffer() {
        return new Float(memory);
    }

    public Double toSubDoubleBuffer() {
        return new Double(memory);
    }

    public static class Byte extends OpenCLSubMemory {
        Byte(cl_mem memory) {
            super(memory);
        }
    }

    public static class Short extends OpenCLSubMemory {
        Short(cl_mem memory) {
            super(memory);
        }
    }

    public static class Integer extends OpenCLSubMemory {
        Integer(cl_mem memory) {
            super(memory);
        }
    }

    public static class Long extends OpenCLSubMemory {
        Long(cl_mem memory) {
            super(memory);
        }
    }

    public static class Float extends OpenCLSubMemory {
        Float(cl_mem memory) {
            super(memory);
        }
    }

    public static class Double extends OpenCLSubMemory {
        Double(cl_mem memory) {
            super(memory);
        }
    }
}
