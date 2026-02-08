package io.github.sypiece.opencl;

import org.jocl.cl_program;

import static org.jocl.CL.*;

public class OpenCLProgram implements AutoCloseable {
    public static OpenCLProgram create(OpenCLContext context, String... source) {
        return new OpenCLProgram(clCreateProgramWithSource(context.context, source.length, source, null, null));
    }

    final cl_program program;

    OpenCLProgram(cl_program program) {
        this.program = program;
    }

    public OpenCLProgram build() {
        clBuildProgram(program, 0, null, null, null, null);
        return this;
    }

    public OpenCLKernel createKernel(String kernelName) {
        return new OpenCLKernel(clCreateKernel(program, kernelName, null));
    }

    @Override
    public void close() {
        clReleaseProgram(program);
    }
}
