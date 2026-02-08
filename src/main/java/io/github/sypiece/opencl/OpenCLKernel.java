package io.github.sypiece.opencl;

import org.jocl.CL;
import org.jocl.Pointer;
import org.jocl.Sizeof;
import org.jocl.cl_kernel;

import static org.jocl.CL.*;

public class OpenCLKernel extends OpenCLInfoObject<cl_kernel> implements AutoCloseable {
    final cl_kernel kernel;

    OpenCLKernel(cl_kernel kernel) {
        super(kernel, CL::clGetKernelInfo);
        this.kernel = kernel;
    }

    public String getFunctionName() {
        return getStringInfo(CL_KERNEL_FUNCTION_NAME);
    }

    public int getNumArgs() {
        return getIntInfo(CL_KERNEL_NUM_ARGS);
    }

    public int getReferenceCount() {
        return getIntInfo(CL_KERNEL_REFERENCE_COUNT);
    }

    public OpenCLContext getContext() {
        return getContextInfo(CL_KERNEL_CONTEXT);
    }

    public OpenCLProgram getProgram() {
        return getProgramInfo(CL_KERNEL_PROGRAM);
    }

    public void setArg(int index, OpenCLMemory memory) {
        clSetKernelArg(kernel, index, Sizeof.cl_mem, Pointer.to(memory.memory));
    }

    public void setArg(int index, byte value) {
        clSetKernelArg(kernel, index, Sizeof.cl_char, Pointer.to(new byte[]{value}));
    }

    public void setArg(int index, short value) {
        clSetKernelArg(kernel, index, Sizeof.cl_short, Pointer.to(new short[]{value}));
    }

    public void setArg(int index, int value) {
        clSetKernelArg(kernel, index, Sizeof.cl_int, Pointer.to(new int[]{value}));
    }

    public void setArg(int index, long value) {
        clSetKernelArg(kernel, index, Sizeof.cl_long, Pointer.to(new long[]{value}));
    }

    public void setArg(int index, float value) {
        clSetKernelArg(kernel, index, Sizeof.cl_float, Pointer.to(new float[]{value}));
    }

    public void setArg(int index, double value) {
        clSetKernelArg(kernel, index, Sizeof.cl_double, Pointer.to(new double[]{value}));
    }

    @Override
    public void close() {
        clReleaseKernel(kernel);
    }
}
