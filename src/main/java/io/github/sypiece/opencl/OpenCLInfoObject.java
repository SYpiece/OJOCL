package io.github.sypiece.opencl;

import org.jocl.*;

abstract class OpenCLInfoObject<T> {
    final T baseObject;
    final InfoGetter<T> infoGetter;

    OpenCLInfoObject(T baseObject, InfoGetter<T> infoGetter) {
        this.baseObject = baseObject;
        this.infoGetter = infoGetter;
    }

    protected long getInfoSize(int paramName) {
        long[] size = new long[1];
        infoGetter.getInfo(baseObject, paramName, 0, null, size);
        return size[0];
    }

    protected String getStringInfo(int paramName) {
        long size = getInfoSize(paramName);
        byte[] buffer = new byte[(int)size / Sizeof.cl_char];
        infoGetter.getInfo(baseObject, paramName, size, Pointer.to(buffer), null);
        return new String(buffer, 0, buffer.length - 1);
    }

    protected boolean getBooleanInfo(int paramName) {
        int[] buffer = new int[1];
        infoGetter.getInfo(baseObject, paramName, Sizeof.cl_int, Pointer.to(buffer), null);
        return buffer[0] != 0;
    }

    protected int getIntInfo(int paramName) {
        int[] buffer = new int[1];
        infoGetter.getInfo(baseObject, paramName, Sizeof.cl_int, Pointer.to(buffer), null);
        return buffer[0];
    }

    protected long getLongInfo(int paramName) {
        long[] buffer = new long[1];
        infoGetter.getInfo(baseObject, paramName, Sizeof.cl_long, Pointer.to(buffer), null);
        return buffer[0];
    }

    protected long getSizeTInfo(int paramName) {
        if (Sizeof.size_t == 4) {
            int[] buffer = new int[1];
            infoGetter.getInfo(baseObject, paramName, Sizeof.size_t, Pointer.to(buffer), null);
            return buffer[0];
        } else if (Sizeof.size_t == 8) {
            long[] buffer = new long[1];
            infoGetter.getInfo(baseObject, paramName, Sizeof.size_t, Pointer.to(buffer), null);
            return buffer[0];
        } else {
            throw new RuntimeException("Unknown size_t size");
        }
    }

    protected OpenCLMemory getMemoryInfo(int paramName) {
        cl_mem[] buffer = new cl_mem[1];
        infoGetter.getInfo(baseObject, paramName, Sizeof.cl_mem, Pointer.to(buffer), null);
        return buffer[0] == null ? null : new OpenCLMemory(buffer[0]);
    }

    protected OpenCLCommandQueue getCommandQueueInfo(int paramName) {
        cl_command_queue[] buffer = new cl_command_queue[1];
        infoGetter.getInfo(baseObject, paramName, Sizeof.cl_command_queue, Pointer.to(buffer), null);
        return buffer[0] == null ? null : new OpenCLCommandQueue(buffer[0]);
    }

    protected OpenCLProgram getProgramInfo(int paramName) {
        cl_program[] buffer = new cl_program[1];
        infoGetter.getInfo(baseObject, paramName, Sizeof.cl_program, Pointer.to(buffer), null);
        return buffer[0] == null ? null : new OpenCLProgram(buffer[0]);
    }

    protected OpenCLContext getContextInfo(int paramName) {
        cl_context[] buffer = new cl_context[1];
        infoGetter.getInfo(baseObject, paramName, Sizeof.cl_context, Pointer.to(buffer), null);
        return buffer[0] == null ? null : new OpenCLContext(buffer[0]);
    }

    public OpenCLDevice getDeviceInfo(int paramName) {
        cl_device_id[] buffer = new cl_device_id[1];
        infoGetter.getInfo(baseObject, paramName, Sizeof.cl_device_id, Pointer.to(buffer), null);
        return buffer[0] == null ? null : new OpenCLDevice(buffer[0]);
    }

    protected OpenCLPlatform getPlatformInfo(int paramName) {
        cl_platform_id[] buffer = new cl_platform_id[1];
        infoGetter.getInfo(baseObject, paramName, Sizeof.cl_platform_id, Pointer.to(buffer), null);
        return buffer[0] == null ? null : new OpenCLPlatform(buffer[0]);
    }

    protected long[] getSizeTArrayInfo(int paramName) {
        long size = getInfoSize(paramName);
        long[] array = new long[(int) size / Sizeof.size_t];
        if (Sizeof.size_t == 4) {
            int[] buffer = new int[(int) size / Sizeof.size_t];
            infoGetter.getInfo(baseObject, paramName, size, Pointer.to(buffer), null);
            for (int i = 0; i < buffer.length; i++) {
                array[i] = buffer[i];
            }
        } else if (Sizeof.size_t == 8) {
            infoGetter.getInfo(baseObject, paramName, size, Pointer.to(array), null);
        } else {
            throw new RuntimeException("Unknown size_t size");
        }
        return array;
    }

    protected OpenCLDevice[] getDeviceArrayInfo(int paramName) {
        long size = getInfoSize(paramName);
        cl_device_id[] buffer = new cl_device_id[(int) size / Sizeof.cl_device_id];
        infoGetter.getInfo(baseObject, paramName, size, Pointer.to(buffer), null);
        OpenCLDevice[] devices = new OpenCLDevice[buffer.length];
        for (int i = 0; i < buffer.length; i++) {
            devices[i] = buffer[i] == null ? null : new OpenCLDevice(buffer[i]);
        }
        return devices;
    }

    interface InfoGetter<T> {
        int getInfo(T baseObject, int paramName, long size, Pointer buffer, long[] sizeRet);
    }
}
