package io.github.sypiece.opencl;

import org.jocl.*;

abstract class OpenCLObject<T extends NativePointerObject> {
    final T object;
    final InfoGetter<T>[] infoGetters;

    public T getObject() {
        return object;
    }

    @SafeVarargs
    OpenCLObject(T object, InfoGetter<T>... infoGetters) {
        this.object = object;
        this.infoGetters = infoGetters;
    }

    protected long getInfoSize(int paramName) {
        return getInfoSize(paramName, 0);
    }

    protected long getInfoSize(int paramName, int getterIndex) {
        long[] size = new long[1];
        infoGetters[getterIndex].getInfo(object, paramName, 0, null, size);
        return size[0];
    }

    protected String getStringInfo(int paramName) {
        return getStringInfo(paramName, 0);
    }

    protected String getStringInfo(int paramName, int getterIndex) {
        long size = getInfoSize(paramName, getterIndex);
        byte[] buffer = new byte[(int)size / Sizeof.cl_char];
        infoGetters[getterIndex].getInfo(object, paramName, size, Pointer.to(buffer), null);
        return new String(buffer, 0, buffer.length - 1);
    }

    protected boolean getBooleanInfo(int paramName) {
        return getBooleanInfo(paramName, 0);
    }

    protected boolean getBooleanInfo(int paramName, int getterIndex) {
        int[] buffer = new int[1];
        infoGetters[getterIndex].getInfo(object, paramName, Sizeof.cl_int, Pointer.to(buffer), null);
        return buffer[0] != 0;
    }

    protected int getIntInfo(int paramName) {
        return getIntInfo(paramName, 0);
    }

    protected int getIntInfo(int paramName, int getterIndex) {
        int[] buffer = new int[1];
        infoGetters[getterIndex].getInfo(object, paramName, Sizeof.cl_int, Pointer.to(buffer), null);
        return buffer[0];
    }

    protected long getLongInfo(int paramName) {
        return getLongInfo(paramName, 0);
    }

    protected long getLongInfo(int paramName, int getterIndex) {
        long[] buffer = new long[1];
        infoGetters[getterIndex].getInfo(object, paramName, Sizeof.cl_long, Pointer.to(buffer), null);
        return buffer[0];
    }

    protected long getSizeTInfo(int paramName) {
        return getSizeTInfo(paramName, 0);
    }

    protected long getSizeTInfo(int paramName, int getterIndex) {
        if (Sizeof.size_t == 4) {
            int[] buffer = new int[1];
            infoGetters[getterIndex].getInfo(object, paramName, Sizeof.size_t, Pointer.to(buffer), null);
            return buffer[0];
        } else if (Sizeof.size_t == 8) {
            long[] buffer = new long[1];
            infoGetters[getterIndex].getInfo(object, paramName, Sizeof.size_t, Pointer.to(buffer), null);
            return buffer[0];
        } else {
            throw new RuntimeException("Unknown size_t size");
        }
    }

    protected OpenCLMemory getMemoryInfo(int paramName) {
        return getMemoryInfo(paramName, 0);
    }

    protected OpenCLMemory getMemoryInfo(int paramName, int getterIndex) {
        cl_mem[] buffer = new cl_mem[1];
        infoGetters[getterIndex].getInfo(object, paramName, Sizeof.cl_mem, Pointer.to(buffer), null);
        return buffer[0] == null ? null : new OpenCLMemory(buffer[0]);
    }

    protected OpenCLCommandQueue getCommandQueueInfo(int paramName) {
        return getCommandQueueInfo(paramName, 0);
    }

    protected OpenCLCommandQueue getCommandQueueInfo(int paramName, int getterIndex) {
        cl_command_queue[] buffer = new cl_command_queue[1];
        infoGetters[getterIndex].getInfo(object, paramName, Sizeof.cl_command_queue, Pointer.to(buffer), null);
        return buffer[0] == null ? null : new OpenCLCommandQueue(buffer[0]);
    }

    protected OpenCLProgram getProgramInfo(int paramName) {
        return getProgramInfo(paramName, 0);
    }

    protected OpenCLProgram getProgramInfo(int paramName, int getterIndex) {
        cl_program[] buffer = new cl_program[1];
        infoGetters[getterIndex].getInfo(object, paramName, Sizeof.cl_program, Pointer.to(buffer), null);
        return buffer[0] == null ? null : new OpenCLProgram(buffer[0]);
    }

    protected OpenCLContext getContextInfo(int paramName) {
        return getContextInfo(paramName, 0);
    }

    protected OpenCLContext getContextInfo(int paramName, int getterIndex) {
        cl_context[] buffer = new cl_context[1];
        infoGetters[getterIndex].getInfo(object, paramName, Sizeof.cl_context, Pointer.to(buffer), null);
        return buffer[0] == null ? null : new OpenCLContext(buffer[0]);
    }

    protected OpenCLDevice getDeviceInfo(int paramName) {
        return getDeviceInfo(paramName, 0);
    }

    protected OpenCLDevice getDeviceInfo(int paramName, int getterIndex) {
        cl_device_id[] buffer = new cl_device_id[1];
        infoGetters[getterIndex].getInfo(object, paramName, Sizeof.cl_device_id, Pointer.to(buffer), null);
        return buffer[0] == null ? null : new OpenCLDevice(buffer[0]);
    }

    protected OpenCLPlatform getPlatformInfo(int paramName) {
        return getPlatformInfo(paramName, 0);
    }

    protected OpenCLPlatform getPlatformInfo(int paramName, int getterIndex) {
        cl_platform_id[] buffer = new cl_platform_id[1];
        infoGetters[getterIndex].getInfo(object, paramName, Sizeof.cl_platform_id, Pointer.to(buffer), null);
        return buffer[0] == null ? null : new OpenCLPlatform(buffer[0]);
    }

    protected long[] getSizeTArrayInfo(int paramName) {
        return getSizeTArrayInfo(paramName, 0);
    }

    protected long[] getSizeTArrayInfo(int paramName, int getterIndex) {
        long size = getInfoSize(paramName);
        long[] array = new long[(int) size / Sizeof.size_t];
        if (Sizeof.size_t == 4) {
            int[] buffer = new int[(int) size / Sizeof.size_t];
            infoGetters[getterIndex].getInfo(object, paramName, size, Pointer.to(buffer), null);
            for (int i = 0; i < buffer.length; i++) {
                array[i] = buffer[i];
            }
        } else if (Sizeof.size_t == 8) {
            infoGetters[getterIndex].getInfo(object, paramName, size, Pointer.to(array), null);
        } else {
            throw new RuntimeException("Unknown size_t size");
        }
        return array;
    }

    protected OpenCLDevice[] getDeviceArrayInfo(int paramName) {
        return getDeviceArrayInfo(paramName, 0);
    }

    protected OpenCLDevice[] getDeviceArrayInfo(int paramName, int getterIndex) {
        long size = getInfoSize(paramName, getterIndex);
        cl_device_id[] buffer = new cl_device_id[(int) size / Sizeof.cl_device_id];
        infoGetters[getterIndex].getInfo(object, paramName, size, Pointer.to(buffer), null);
        OpenCLDevice[] devices = new OpenCLDevice[buffer.length];
        for (int i = 0; i < buffer.length; i++) {
            devices[i] = buffer[i] == null ? null : new OpenCLDevice(buffer[i]);
        }
        return devices;
    }

    protected void getInfo(int paramName, long size, Pointer buffer, long[] sizeRet) {
        infoGetters[0].getInfo(object, paramName, size, buffer, sizeRet);
    }

    protected void getInfo(int paramName, int getterIndex, long size, Pointer buffer, long[] sizeRet) {
        infoGetters[getterIndex].getInfo(object, paramName, size, buffer, sizeRet);
    }

    interface InfoGetter<T> {
        int getInfo(T baseObject, int paramName, long size, Pointer buffer, long[] sizeRet);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final OpenCLObject other = (OpenCLObject) obj;
        return object.equals(other.object);
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 29 * hash + object.hashCode();
        return hash;
    }
}
