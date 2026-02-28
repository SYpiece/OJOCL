package io.github.sypiece.opencl;

import org.jocl.*;

import static org.jocl.CL.*;
import static org.jocl.CL.clWaitForEvents;

public class OpenCLCommandQueue extends OpenCLObject<cl_command_queue> implements AutoCloseable {
    public static OpenCLCommandQueue create(OpenCLContext context, OpenCLDevice device) {
        return create(context, device, null);
    }

    public static OpenCLCommandQueue create(OpenCLContext context, OpenCLDevice device, Properties properties) {
        return new OpenCLCommandQueue(clCreateCommandQueueWithProperties(context.context, device.deviceID, properties == null ? null : properties.properties, null));
    }

    final cl_command_queue commandQueue;

    OpenCLCommandQueue(cl_command_queue commandQueue) {
        super(commandQueue, CL::clGetCommandQueueInfo);
        this.commandQueue = commandQueue;
    }

    public OpenCLContext getContext() {
        return getContextInfo(CL_QUEUE_CONTEXT);
    }

    public OpenCLDevice getDevice() {
        return getDeviceInfo(CL_QUEUE_DEVICE);
    }

    public int getReferenceCount() {
        return getIntInfo(CL_QUEUE_REFERENCE_COUNT);
    }

    public byte[] readBuffer(OpenCLMemory.Byte memory) {
        long size = memory.getSize();
        byte[] values = new byte[(int) size / Sizeof.cl_char];
        clEnqueueReadBuffer(commandQueue, memory.memory, true, 0, size, Pointer.to(values), 0, null, null);
        return values;
    }

    public short[] readBuffer(OpenCLMemory.Short memory) {
        long size = memory.getSize();
        short[] values = new short[(int) size / Sizeof.cl_short];
        clEnqueueReadBuffer(commandQueue, memory.memory, true, 0, size, Pointer.to(values), 0, null, null);
        return values;
    }

    public int[] readBuffer(OpenCLMemory.Integer memory) {
        long size = memory.getSize();
        int[] values = new int[(int) size / Sizeof.cl_int];
        clEnqueueReadBuffer(commandQueue, memory.memory, true, 0, size, Pointer.to(values), 0, null, null);
        return values;
    }

    public long[] readBuffer(OpenCLMemory.Long memory) {
        long size = memory.getSize();
        long[] values = new long[(int) size / Sizeof.cl_long];
        clEnqueueReadBuffer(commandQueue, memory.memory, true, 0, size, Pointer.to(values), 0, null, null);
        return values;
    }

    public float[] readBuffer(OpenCLMemory.Float memory) {
        long size = memory.getSize();
        float[] values = new float[(int) size / Sizeof.cl_float];
        clEnqueueReadBuffer(commandQueue, memory.memory, true, 0, size, Pointer.to(values), 0, null, null);
        return values;
    }

    public double[] readBuffer(OpenCLMemory.Double memory) {
        long size = memory.getSize();
        double[] values = new double[(int) size / Sizeof.cl_double];
        clEnqueueReadBuffer(commandQueue, memory.memory, true, 0, size, Pointer.to(values), 0, null, null);
        return values;
    }

    public void readBuffer(OpenCLMemory.Byte memory, byte[] values) {
        clEnqueueReadBuffer(commandQueue, memory.memory, true, 0, (long) Sizeof.cl_char * values.length, Pointer.to(values), 0, null, null);
    }

    public void readBuffer(OpenCLMemory.Short memory, short[] values) {
        clEnqueueReadBuffer(commandQueue, memory.memory, true, 0, (long) Sizeof.cl_short * values.length, Pointer.to(values), 0, null, null);
    }

    public void readBuffer(OpenCLMemory.Integer memory, int[] values) {
        clEnqueueReadBuffer(commandQueue, memory.memory, true, 0, (long) Sizeof.cl_int * values.length, Pointer.to(values), 0, null, null);
    }

    public void readBuffer(OpenCLMemory.Long memory, long[] values) {
        clEnqueueReadBuffer(commandQueue, memory.memory, true, 0, (long) Sizeof.cl_long * values.length, Pointer.to(values), 0, null, null);
    }

    public void readBuffer(OpenCLMemory.Float memory, float[] values) {
        clEnqueueReadBuffer(commandQueue, memory.memory, true, 0, (long) Sizeof.cl_float * values.length, Pointer.to(values), 0, null, null);
    }

    public void readBuffer(OpenCLMemory.Double memory, double[] values) {
        clEnqueueReadBuffer(commandQueue, memory.memory, true, 0, (long) Sizeof.cl_double * values.length, Pointer.to(values), 0, null, null);
    }

    public OpenCLEvent readBufferAsync(OpenCLMemory.Byte memory, byte[] values) {
        return readBufferAsync(memory, values, (OpenCLEvent[]) null);
    }

    public OpenCLEvent readBufferAsync(OpenCLMemory.Short memory, short[] values) {
        return readBufferAsync(memory, values, (OpenCLEvent[]) null);
    }

    public OpenCLEvent readBufferAsync(OpenCLMemory.Integer memory, int[] values) {
        return readBufferAsync(memory, values, (OpenCLEvent[]) null);
    }

    public OpenCLEvent readBufferAsync(OpenCLMemory.Long memory, long[] values) {
        return readBufferAsync(memory, values, (OpenCLEvent[]) null);
    }

    public OpenCLEvent readBufferAsync(OpenCLMemory.Float memory, float[] values) {
        return readBufferAsync(memory, values, (OpenCLEvent[]) null);
    }

    public OpenCLEvent readBufferAsync(OpenCLMemory.Double memory, double[] values) {
        return readBufferAsync(memory, values, (OpenCLEvent[]) null);
    }

    public OpenCLEvent readBufferAsync(OpenCLMemory.Byte memory, byte[] values, OpenCLEvent... waitList) {
        cl_event[] events = toCLEventArray(waitList);
        cl_event event = new cl_event();
        clEnqueueReadBuffer(
                commandQueue, memory.memory, false, 0, (long) Sizeof.cl_char * values.length, Pointer.to(values),
                events == null ? 0 : events.length, events, event
        );
        return new OpenCLEvent(event);
    }

    public OpenCLEvent readBufferAsync(OpenCLMemory.Short memory, short[] values, OpenCLEvent... waitList) {
        cl_event[] events = toCLEventArray(waitList);
        cl_event event = new cl_event();
        clEnqueueReadBuffer(
                commandQueue, memory.memory, false, 0, (long) Sizeof.cl_short * values.length, Pointer.to(values),
                events == null ? 0 : events.length, events, event
        );
        return new OpenCLEvent(event);
    }

    public OpenCLEvent readBufferAsync(OpenCLMemory.Integer memory, int[] values, OpenCLEvent... waitList) {
        cl_event[] events = toCLEventArray(waitList);
        cl_event event = new cl_event();
        clEnqueueReadBuffer(
                commandQueue, memory.memory, false, 0, (long) Sizeof.cl_int * values.length, Pointer.to(values),
                events == null ? 0 : events.length, events, event
        );
        return new OpenCLEvent(event);
    }

    public OpenCLEvent readBufferAsync(OpenCLMemory.Long memory, long[] values, OpenCLEvent... waitList) {
        cl_event[] events = toCLEventArray(waitList);
        cl_event event = new cl_event();
        clEnqueueReadBuffer(
                commandQueue, memory.memory, false, 0, (long) Sizeof.cl_long * values.length, Pointer.to(values),
                events == null ? 0 : events.length, events, event
        );
        return new OpenCLEvent(event);
    }

    public OpenCLEvent readBufferAsync(OpenCLMemory.Float memory, float[] values, OpenCLEvent... waitList) {
        cl_event[] events = toCLEventArray(waitList);
        cl_event event = new cl_event();
        clEnqueueReadBuffer(
                commandQueue, memory.memory, false, 0, (long) Sizeof.cl_float * values.length, Pointer.to(values),
                events == null ? 0 : events.length, events, event
        );
        return new OpenCLEvent(event);
    }

    public OpenCLEvent readBufferAsync(OpenCLMemory.Double memory, double[] values, OpenCLEvent... waitList) {
        cl_event[] events = toCLEventArray(waitList);
        cl_event event = new cl_event();
        clEnqueueReadBuffer(
                commandQueue, memory.memory, false, 0, (long) Sizeof.cl_double * values.length, Pointer.to(values),
                events == null ? 0 : events.length, events, event
        );
        return new OpenCLEvent(event);
    }

    public void writeBuffer(OpenCLMemory.Byte memory, byte[] values) {
        clEnqueueWriteBuffer(commandQueue, memory.memory, true, 0, (long) Sizeof.cl_char * values.length, Pointer.to(values), 0, null, null);
    }

    public void writeBuffer(OpenCLMemory.Short memory, short[] values) {
        clEnqueueWriteBuffer(commandQueue, memory.memory, true, 0, (long) Sizeof.cl_short * values.length, Pointer.to(values), 0, null, null);
    }

    public void writeBuffer(OpenCLMemory.Integer memory, int[] values) {
        clEnqueueWriteBuffer(commandQueue, memory.memory, true, 0, (long) Sizeof.cl_int * values.length, Pointer.to(values), 0, null, null);
    }

    public void writeBuffer(OpenCLMemory.Long memory, long[] values) {
        clEnqueueWriteBuffer(commandQueue, memory.memory, true, 0, (long) Sizeof.cl_long * values.length, Pointer.to(values), 0, null, null);
    }

    public void writeBuffer(OpenCLMemory.Float memory, float[] values) {
        clEnqueueWriteBuffer(commandQueue, memory.memory, true, 0, (long) Sizeof.cl_float * values.length, Pointer.to(values), 0, null, null);
    }

    public void writeBuffer(OpenCLMemory.Double memory, double[] values) {
        clEnqueueWriteBuffer(commandQueue, memory.memory, true, 0, (long) Sizeof.cl_double * values.length, Pointer.to(values), 0, null, null);
    }

    public OpenCLEvent writeBufferAsync(OpenCLMemory.Byte memory, byte[] values) {
        return writeBufferAsync(memory, values, (OpenCLEvent[]) null);
    }

    public OpenCLEvent writeBufferAsync(OpenCLMemory.Short memory, short[] values) {
        return writeBufferAsync(memory, values, (OpenCLEvent[]) null);
    }

    public OpenCLEvent writeBufferAsync(OpenCLMemory.Integer memory, int[] values) {
        return writeBufferAsync(memory, values, (OpenCLEvent[]) null);
    }

    public OpenCLEvent writeBufferAsync(OpenCLMemory.Long memory, long[] values) {
        return writeBufferAsync(memory, values, (OpenCLEvent[]) null);
    }

    public OpenCLEvent writeBufferAsync(OpenCLMemory.Float memory, float[] values) {
        return writeBufferAsync(memory, values, (OpenCLEvent[]) null);
    }

    public OpenCLEvent writeBufferAsync(OpenCLMemory.Double memory, double[] values) {
        return writeBufferAsync(memory, values, (OpenCLEvent[]) null);
    }

    public OpenCLEvent writeBufferAsync(OpenCLMemory.Byte memory, byte[] values, OpenCLEvent... waitList) {
        cl_event[] events = toCLEventArray(waitList);
        cl_event event = new cl_event();
        clEnqueueWriteBuffer(
                commandQueue, memory.memory, false, 0, (long) Sizeof.cl_char * values.length, Pointer.to(values),
                events == null ? 0 : events.length, events, event
        );
        return new OpenCLEvent(event);
    }

    public OpenCLEvent writeBufferAsync(OpenCLMemory.Short memory, short[] values, OpenCLEvent... waitList) {
        cl_event[] events = toCLEventArray(waitList);
        cl_event event = new cl_event();
        clEnqueueWriteBuffer(
                commandQueue, memory.memory, false, 0, (long) Sizeof.cl_short * values.length, Pointer.to(values),
                events == null ? 0 : events.length, events, event
        );
        return new OpenCLEvent(event);
    }

    public OpenCLEvent writeBufferAsync(OpenCLMemory.Integer memory, int[] values, OpenCLEvent... waitList) {
        cl_event[] events = toCLEventArray(waitList);
        cl_event event = new cl_event();
        clEnqueueWriteBuffer(
                commandQueue, memory.memory, false, 0, (long) Sizeof.cl_int * values.length, Pointer.to(values),
                events == null ? 0 : events.length, events, event
        );
        return new OpenCLEvent(event);
    }

    public OpenCLEvent writeBufferAsync(OpenCLMemory.Long memory, long[] values, OpenCLEvent... waitList) {
        cl_event[] events = toCLEventArray(waitList);
        cl_event event = new cl_event();
        clEnqueueWriteBuffer(
                commandQueue, memory.memory, false, 0, (long) Sizeof.cl_long * values.length, Pointer.to(values),
                events == null ? 0 : events.length, events, event
        );
        return new OpenCLEvent(event);
    }

    public OpenCLEvent writeBufferAsync(OpenCLMemory.Float memory, float[] values, OpenCLEvent... waitList) {
        cl_event[] events = toCLEventArray(waitList);
        cl_event event = new cl_event();
        clEnqueueWriteBuffer(
                commandQueue, memory.memory, false, 0, (long) Sizeof.cl_float * values.length, Pointer.to(values),
                events == null ? 0 : events.length, events, event
        );
        return new OpenCLEvent(event);
    }

    public OpenCLEvent writeBufferAsync(OpenCLMemory.Double memory, double[] values, OpenCLEvent... waitList) {
        cl_event[] events = toCLEventArray(waitList);
        cl_event event = new cl_event();
        clEnqueueWriteBuffer(
                commandQueue, memory.memory, false, 0, (long) Sizeof.cl_double * values.length, Pointer.to(values),
                events == null ? 0 : events.length, events, event
        );
        return new OpenCLEvent(event);
    }

    public void executeKernel(OpenCLKernel kernel, Range range) {
        cl_event event = new cl_event();
        clEnqueueNDRangeKernel(commandQueue, kernel.kernel, range.workDim, range.globalWorkOffset, range.globalWorkSize, range.localWorkSize, 0, null, event);
        clWaitForEvents(1, new cl_event[] { event });
        clReleaseEvent(event);
    }

    public OpenCLEvent executeKernelAsync(OpenCLKernel kernel, Range range) {
        return executeKernelAsync(kernel, range, (OpenCLEvent[]) null);
    }

    public OpenCLEvent executeKernelAsync(OpenCLKernel kernel, Range range, OpenCLEvent... waitList) {
        cl_event[] events = toCLEventArray(waitList);
        cl_event event = new cl_event();
        clEnqueueNDRangeKernel(
                commandQueue, kernel.kernel, range.workDim, range.globalWorkOffset, range.globalWorkSize, range.localWorkSize,
                events == null ? 0 : events.length, events, event
        );
        return new OpenCLEvent(event);
    }

    private cl_event[] toCLEventArray(OpenCLEvent[] waitList) {
        if (waitList == null) {
            return null;
        }
        cl_event[] events = new cl_event[waitList.length];
        for (int i = 0; i < waitList.length; i++) {
            events[i] = waitList[i].event;
        }
        return events;
    }

    public void finish() {
        clFinish(commandQueue);
    }

    @Override
    public void close() {
        clReleaseCommandQueue(commandQueue);
    }

    public static class Properties {
        public static final int
                PROPERTIES = CL_QUEUE_PROPERTIES,
                SIZE = CL_QUEUE_SIZE;

        public static final long
                OUT_OF_ORDER_EXEC_MODE_ENABLE = CL_QUEUE_OUT_OF_ORDER_EXEC_MODE_ENABLE,
                PROFILING_ENABLE = CL_QUEUE_PROFILING_ENABLE,
                ON_DEVICE = CL_QUEUE_ON_DEVICE,
                ON_DEVICE_DEFAULT = CL_QUEUE_ON_DEVICE_DEFAULT;

        final cl_queue_properties properties = new cl_queue_properties();

        public Properties() {}

        public Properties addOutOfOrderExecModeEnable() {
            properties.addProperty(CL_QUEUE_PROPERTIES, OUT_OF_ORDER_EXEC_MODE_ENABLE);
            return this;
        }

        public Properties addProfilingEnable() {
            properties.addProperty(CL_QUEUE_PROPERTIES, PROFILING_ENABLE);
            return this;
        }

        public Properties addOnDevice() {
            properties.addProperty(CL_QUEUE_PROPERTIES, ON_DEVICE);
            return this;
        }

        public Properties addOnDeviceDefault() {
            properties.addProperty(CL_QUEUE_PROPERTIES, ON_DEVICE_DEFAULT);
            return this;
        }

        public Properties add(int property, long value) {
            properties.addProperty(property, value);
            return this;
        }
    }

    public static class Range {
        final int workDim;

        final long[]
                globalWorkOffset,
                globalWorkSize,
                localWorkSize;

        public static Range create(long globalWorkSize) {
            return createND(1, null, new long[]{globalWorkSize}, null);
        }

        public static Range create(long globalWorkOffset, long globalWorkSize) {
            return createND(1, new long[]{globalWorkOffset}, new long[]{globalWorkSize}, null);
        }

        public static Range create(long globalWorkOffset, long globalWorkSize, long localWorkSize) {
            return createND(1, new long[]{globalWorkOffset}, new long[]{globalWorkSize}, new long[]{localWorkSize});
        }

        public static Range create2D(long globalWorkSizeX, long globalWorkSizeY) {
            return createND(2, null, new long[]{globalWorkSizeX, globalWorkSizeY}, null);
        }

        public static Range create2D(long globalWorkOffsetX, long globalWorkOffsetY, long globalWorkSizeX, long globalWorkSizeY) {
            return createND(2, new long[]{globalWorkOffsetX, globalWorkOffsetY}, new long[]{globalWorkSizeX, globalWorkSizeY}, null);
        }

        public static Range create2D(long globalWorkOffsetX, long globalWorkOffsetY, long globalWorkSizeX, long globalWorkSizeY, long localWorkSizeX, long localWorkSizeY) {
            return createND(2, new long[]{globalWorkOffsetX, globalWorkOffsetY}, new long[]{globalWorkSizeX, globalWorkSizeY}, new long[]{localWorkSizeX, localWorkSizeY});
        }

        public static Range create3D(long globalWorkSizeX, long globalWorkSizeY, long globalWorkSizeZ) {
            return createND(3, null, new long[]{globalWorkSizeX, globalWorkSizeY, globalWorkSizeZ}, null);
        }

        public static Range create3D(long globalWorkOffsetX, long globalWorkOffsetY, long globalWorkOffsetZ, long globalWorkSizeX, long globalWorkSizeY, long globalWorkSizeZ) {
            return createND(3, new long[]{globalWorkOffsetX, globalWorkOffsetY, globalWorkOffsetZ}, new long[]{globalWorkSizeX, globalWorkSizeY, globalWorkSizeZ}, null);
        }

        public static Range create3D(long globalWorkOffsetX, long globalWorkOffsetY, long globalWorkOffsetZ, long globalWorkSizeX, long globalWorkSizeY, long globalWorkSizeZ, long localWorkSizeX, long localWorkSizeY, long localWorkSizeZ) {
            return createND(3, new long[]{globalWorkOffsetX, globalWorkOffsetY, globalWorkOffsetZ}, new long[]{globalWorkSizeX, globalWorkSizeY, globalWorkSizeZ}, new long[]{localWorkSizeX, localWorkSizeY, localWorkSizeZ});
        }

        public static Range createND(int workDim, long[] globalWorkOffset, long[] globalWorkSize, long[] localWorkSize) {
            if (globalWorkSize == null) {
                throw new IllegalArgumentException("globalWorkSize must not be null");
            }
            if (workDim != globalWorkSize.length ||
                    (globalWorkOffset != null && workDim != globalWorkOffset.length) ||
                    (localWorkSize != null && workDim != localWorkSize.length)) {
                throw new IllegalArgumentException("workDim must be equal to globalWorkOffset.length, globalWorkSize.length and localWorkSize.length if there isn't null");
            }
            return new Range(workDim, globalWorkOffset, globalWorkSize, localWorkSize);
        }

        Range(int workDim, long[] globalWorkOffset, long[] globalWorkSize, long[] localWorkSize) {
            this.workDim = workDim;
            this.globalWorkOffset = globalWorkOffset;
            this.globalWorkSize = globalWorkSize;
            this.localWorkSize = localWorkSize;
        }
    }
}
