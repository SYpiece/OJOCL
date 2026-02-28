package io.github.sypiece.opencl;

import org.jocl.*;

import static org.jocl.CL.*;

public class OpenCLProgram extends OpenCLObject<cl_program> implements AutoCloseable {
    public static OpenCLProgram createWithSource(OpenCLContext context, String... source) {
        return new OpenCLProgram(clCreateProgramWithSource(context.context, source.length, source, null, null));
    }

    public static OpenCLProgram createWithBinary(OpenCLContext context, OpenCLDevice[] devices, Binary[] binaries) {
        cl_device_id[] deviceIDs = new cl_device_id[devices.length];
        for (int i = 0; i < devices.length; i++) {
            deviceIDs[i] = devices[i].deviceID;
        }
        return new OpenCLProgram(clCreateProgramWithBinary(context.context, devices.length, deviceIDs, getBinariesSize(binaries), getBinaries(binaries), null, null));
    }

    private static long[] getBinariesSize(Binary[] binaries) {
        long[] sizes = new long[binaries.length];
        for (int i = 0; i < binaries.length; i++) {
            sizes[i] = binaries[i].getSize();
        }
        return sizes;
    }

    private static byte[][] getBinaries(Binary[] binaries) {
        byte[][] binariesArray = new byte[binaries.length][];
        for (int i = 0; i < binaries.length; i++) {
            binariesArray[i] = binaries[i].getBytes();
        }
        return binariesArray;
    }

    final cl_program program;

    OpenCLProgram(cl_program program) {
        super(program, CL::clGetProgramInfo);
        this.program = program;
    }

    public int getReferenceCount() {
        return getIntInfo(CL_PROGRAM_REFERENCE_COUNT);
    }

    public OpenCLContext getContext() {
        return getContextInfo(CL_PROGRAM_CONTEXT);
    }

    public int getDeviceCount() {
        return getIntInfo(CL_PROGRAM_NUM_DEVICES);
    }

    public OpenCLDevice[] getDevices() {
        return getDeviceArrayInfo(CL_PROGRAM_DEVICES);
    }

    public String getSource() {
        return getStringInfo(CL_PROGRAM_SOURCE);
    }

    public long[] getBinarySizes() {
        return getSizeTArrayInfo(CL_PROGRAM_BINARY_SIZES);
    }

    public Binary[] getBinaries() {
        long[] binarySizes = getBinarySizes();
        Binary[] binaries = new Binary[binarySizes.length];
        Pointer[] pointers = new Pointer[binarySizes.length];
        for (int i = 0; i < binarySizes.length; i++) {
            byte[] bytes = new byte[(int) binarySizes[i]];
            binaries[i] = new Binary(bytes);
            pointers[i] = Pointer.to(binaries[i].binary);
        }
        getInfo(CL_PROGRAM_BINARIES, (long) pointers.length * Sizeof.POINTER, Pointer.to(pointers), null);
        return binaries;
    }

    public BuildInfo getBuildInfo(OpenCLDevice device) {
        return new BuildInfo(program, device.deviceID);
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

    public static class Binary {

        final byte[] binary;

        public byte[] getBytes() {
            return binary;
        }

        public int getSize() {
            return binary.length;
        }

        public Binary(byte[] binary) {
            this.binary = binary;
        }
    }

    public static class BuildInfo extends OpenCLObject<cl_program> {
        BuildInfo(cl_program program, cl_device_id deviceId) {
            super(program, (baseObject, paramName, size, buffer, sizeRet) -> clGetProgramBuildInfo(baseObject, deviceId, paramName, size, buffer, sizeRet));
        }

        public Status getStatus() {
            return Status.valueOf(getIntInfo(CL_PROGRAM_BUILD_STATUS));
        }

        public String getOptions() {
            return getStringInfo(CL_PROGRAM_BUILD_OPTIONS);
        }

        public String getLog() {
            return getStringInfo(CL_PROGRAM_BUILD_LOG);
        }

        public enum Status {
            None(CL_BUILD_NONE),
            Error(CL_BUILD_ERROR),
            Success(CL_BUILD_SUCCESS),
            InProgress(CL_BUILD_IN_PROGRESS);

            final int value;
            Status(int value) {
                this.value = value;
            }

            static Status valueOf(int value) {
                for (Status status : values()) {
                    if (status.value == value) {
                        return status;
                    }
                }
                return null;
            }
        }
    }
}
