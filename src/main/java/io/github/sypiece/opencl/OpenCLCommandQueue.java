package io.github.sypiece.opencl;

import org.jocl.*;

import static org.jocl.CL.*;

public class OpenCLCommandQueue extends OpenCLInfoObject<cl_command_queue> implements AutoCloseable {
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

    public byte[] enqueueReadBuffer(OpenCLMemory.Byte memory) {
        long size = memory.getSize();
        byte[] values = new byte[(int) size / Sizeof.cl_char];
        clEnqueueReadBuffer(commandQueue, memory.memory, true, 0, size, Pointer.to(values), 0, null, null);
        return values;
    }

    public short[] enqueueReadBuffer(OpenCLMemory.Short memory) {
        long size = memory.getSize();
        short[] values = new short[(int) size / Sizeof.cl_short];
        clEnqueueReadBuffer(commandQueue, memory.memory, true, 0, size, Pointer.to(values), 0, null, null);
        return values;
    }

    public int[] enqueueReadBuffer(OpenCLMemory.Integer memory) {
        long size = memory.getSize();
        int[] values = new int[(int) size / Sizeof.cl_int];
        clEnqueueReadBuffer(commandQueue, memory.memory, true, 0, size, Pointer.to(values), 0, null, null);
        return values;
    }

    public long[] enqueueReadBuffer(OpenCLMemory.Long memory) {
        long size = memory.getSize();
        long[] values = new long[(int) size / Sizeof.cl_long];
        clEnqueueReadBuffer(commandQueue, memory.memory, true, 0, size, Pointer.to(values), 0, null, null);
        return values;
    }

    public float[] enqueueReadBuffer(OpenCLMemory.Float memory) {
        long size = memory.getSize();
        float[] values = new float[(int) size / Sizeof.cl_float];
        clEnqueueReadBuffer(commandQueue, memory.memory, true, 0, size, Pointer.to(values), 0, null, null);
        return values;
    }

    public double[] enqueueReadBuffer(OpenCLMemory.Double memory) {
        long size = memory.getSize();
        double[] values = new double[(int) size / Sizeof.cl_double];
        clEnqueueReadBuffer(commandQueue, memory.memory, true, 0, size, Pointer.to(values), 0, null, null);
        return values;
    }

    public void enqueueReadBuffer(OpenCLMemory.Byte memory, byte[] values) {
        clEnqueueReadBuffer(commandQueue, memory.memory, true, 0, (long) Sizeof.cl_char * values.length, Pointer.to(values), 0, null, null);
    }

    public void enqueueReadBuffer(OpenCLMemory.Short memory, short[] values) {
        clEnqueueReadBuffer(commandQueue, memory.memory, true, 0, (long) Sizeof.cl_short * values.length, Pointer.to(values), 0, null, null);
    }

    public void enqueueReadBuffer(OpenCLMemory.Integer memory, int[] values) {
        clEnqueueReadBuffer(commandQueue, memory.memory, true, 0, (long) Sizeof.cl_int * values.length, Pointer.to(values), 0, null, null);
    }

    public void enqueueReadBuffer(OpenCLMemory.Long memory, long[] values) {
        clEnqueueReadBuffer(commandQueue, memory.memory, true, 0, (long) Sizeof.cl_long * values.length, Pointer.to(values), 0, null, null);
    }

    public void enqueueReadBuffer(OpenCLMemory.Float memory, float[] values) {
        clEnqueueReadBuffer(commandQueue, memory.memory, true, 0, (long) Sizeof.cl_float * values.length, Pointer.to(values), 0, null, null);
    }

    public void enqueueReadBuffer(OpenCLMemory.Double memory, double[] values) {
        clEnqueueReadBuffer(commandQueue, memory.memory, true, 0, (long) Sizeof.cl_double * values.length, Pointer.to(values), 0, null, null);
    }

    public OpenCLEvent enqueueReadBufferAsync(OpenCLMemory.Byte memory, byte[] values) {
        return enqueueReadBufferAsync(memory, values, null);
    }

    public OpenCLEvent enqueueReadBufferAsync(OpenCLMemory.Short memory, short[] values) {
        return enqueueReadBufferAsync(memory, values, null);
    }

    public OpenCLEvent enqueueReadBufferAsync(OpenCLMemory.Integer memory, int[] values) {
        return enqueueReadBufferAsync(memory, values, null);
    }

    public OpenCLEvent enqueueReadBufferAsync(OpenCLMemory.Long memory, long[] values) {
        return enqueueReadBufferAsync(memory, values, null);
    }

    public OpenCLEvent enqueueReadBufferAsync(OpenCLMemory.Float memory, float[] values) {
        return enqueueReadBufferAsync(memory, values, null);
    }

    public OpenCLEvent enqueueReadBufferAsync(OpenCLMemory.Double memory, double[] values) {
        return enqueueReadBufferAsync(memory, values, null);
    }

    public OpenCLEvent enqueueReadBufferAsync(OpenCLMemory.Byte memory, byte[] values, OpenCLEvent[] waitList) {
        cl_event[] events = toCLEventArray(waitList);
        cl_event event = new cl_event();
        clEnqueueReadBuffer(
                commandQueue, memory.memory, false, 0, (long) Sizeof.cl_char * values.length, Pointer.to(values),
                events == null ? 0 : events.length, events, event
        );
        return new OpenCLEvent(event);
    }

    public OpenCLEvent enqueueReadBufferAsync(OpenCLMemory.Short memory, short[] values, OpenCLEvent[] waitList) {
        cl_event[] events = toCLEventArray(waitList);
        cl_event event = new cl_event();
        clEnqueueReadBuffer(
                commandQueue, memory.memory, false, 0, (long) Sizeof.cl_short * values.length, Pointer.to(values),
                events == null ? 0 : events.length, events, event
        );
        return new OpenCLEvent(event);
    }

    public OpenCLEvent enqueueReadBufferAsync(OpenCLMemory.Integer memory, int[] values, OpenCLEvent[] waitList) {
        cl_event[] events = toCLEventArray(waitList);
        cl_event event = new cl_event();
        clEnqueueReadBuffer(
                commandQueue, memory.memory, false, 0, (long) Sizeof.cl_int * values.length, Pointer.to(values),
                events == null ? 0 : events.length, events, event
        );
        return new OpenCLEvent(event);
    }

    public OpenCLEvent enqueueReadBufferAsync(OpenCLMemory.Long memory, long[] values, OpenCLEvent[] waitList) {
        cl_event[] events = toCLEventArray(waitList);
        cl_event event = new cl_event();
        clEnqueueReadBuffer(
                commandQueue, memory.memory, false, 0, (long) Sizeof.cl_long * values.length, Pointer.to(values),
                events == null ? 0 : events.length, events, event
        );
        return new OpenCLEvent(event);
    }

    public OpenCLEvent enqueueReadBufferAsync(OpenCLMemory.Float memory, float[] values, OpenCLEvent[] waitList) {
        cl_event[] events = toCLEventArray(waitList);
        cl_event event = new cl_event();
        clEnqueueReadBuffer(
                commandQueue, memory.memory, false, 0, (long) Sizeof.cl_float * values.length, Pointer.to(values),
                events == null ? 0 : events.length, events, event
        );
        return new OpenCLEvent(event);
    }

    public OpenCLEvent enqueueReadBufferAsync(OpenCLMemory.Double memory, double[] values, OpenCLEvent[] waitList) {
        cl_event[] events = toCLEventArray(waitList);
        cl_event event = new cl_event();
        clEnqueueReadBuffer(
                commandQueue, memory.memory, false, 0, (long) Sizeof.cl_double * values.length, Pointer.to(values),
                events == null ? 0 : events.length, events, event
        );
        return new OpenCLEvent(event);
    }

    public void enqueueWriteBuffer(OpenCLMemory.Byte memory, byte[] values) {
        clEnqueueWriteBuffer(commandQueue, memory.memory, true, 0, (long) Sizeof.cl_char * values.length, Pointer.to(values), 0, null, null);
    }

    public void enqueueWriteBuffer(OpenCLMemory.Short memory, short[] values) {
        clEnqueueWriteBuffer(commandQueue, memory.memory, true, 0, (long) Sizeof.cl_short * values.length, Pointer.to(values), 0, null, null);
    }

    public void enqueueWriteBuffer(OpenCLMemory.Integer memory, int[] values) {
        clEnqueueWriteBuffer(commandQueue, memory.memory, true, 0, (long) Sizeof.cl_int * values.length, Pointer.to(values), 0, null, null);
    }

    public void enqueueWriteBuffer(OpenCLMemory.Long memory, long[] values) {
        clEnqueueWriteBuffer(commandQueue, memory.memory, true, 0, (long) Sizeof.cl_long * values.length, Pointer.to(values), 0, null, null);
    }

    public void enqueueWriteBuffer(OpenCLMemory.Float memory, float[] values) {
        clEnqueueWriteBuffer(commandQueue, memory.memory, true, 0, (long) Sizeof.cl_float * values.length, Pointer.to(values), 0, null, null);
    }

    public void enqueueWriteBuffer(OpenCLMemory.Double memory, double[] values) {
        clEnqueueWriteBuffer(commandQueue, memory.memory, true, 0, (long) Sizeof.cl_double * values.length, Pointer.to(values), 0, null, null);
    }

    public OpenCLEvent enqueueWriteBufferAsync(OpenCLMemory.Byte memory, byte[] values) {
        return enqueueWriteBufferAsync(memory, values, null);
    }

    public OpenCLEvent enqueueWriteBufferAsync(OpenCLMemory.Short memory, short[] values) {
        return enqueueWriteBufferAsync(memory, values, null);
    }

    public OpenCLEvent enqueueWriteBufferAsync(OpenCLMemory.Integer memory, int[] values) {
        return enqueueWriteBufferAsync(memory, values, null);
    }

    public OpenCLEvent enqueueWriteBufferAsync(OpenCLMemory.Long memory, long[] values) {
        return enqueueWriteBufferAsync(memory, values, null);
    }

    public OpenCLEvent enqueueWriteBufferAsync(OpenCLMemory.Float memory, float[] values) {
        return enqueueWriteBufferAsync(memory, values, null);
    }

    public OpenCLEvent enqueueWriteBufferAsync(OpenCLMemory.Double memory, double[] values) {
        return enqueueWriteBufferAsync(memory, values, null);
    }

    public OpenCLEvent enqueueWriteBufferAsync(OpenCLMemory.Byte memory, byte[] values, OpenCLEvent[] waitList) {
        cl_event[] events = toCLEventArray(waitList);
        cl_event event = new cl_event();
        clEnqueueWriteBuffer(
                commandQueue, memory.memory, false, 0, (long) Sizeof.cl_char * values.length, Pointer.to(values),
                events == null ? 0 : events.length, events, event
        );
        return new OpenCLEvent(event);
    }

    public OpenCLEvent enqueueWriteBufferAsync(OpenCLMemory.Short memory, short[] values, OpenCLEvent[] waitList) {
        cl_event[] events = toCLEventArray(waitList);
        cl_event event = new cl_event();
        clEnqueueWriteBuffer(
                commandQueue, memory.memory, false, 0, (long) Sizeof.cl_short * values.length, Pointer.to(values),
                events == null ? 0 : events.length, events, event
        );
        return new OpenCLEvent(event);
    }

    public OpenCLEvent enqueueWriteBufferAsync(OpenCLMemory.Integer memory, int[] values, OpenCLEvent[] waitList) {
        cl_event[] events = toCLEventArray(waitList);
        cl_event event = new cl_event();
        clEnqueueWriteBuffer(
                commandQueue, memory.memory, false, 0, (long) Sizeof.cl_int * values.length, Pointer.to(values),
                events == null ? 0 : events.length, events, event
        );
        return new OpenCLEvent(event);
    }

    public OpenCLEvent enqueueWriteBufferAsync(OpenCLMemory.Long memory, long[] values, OpenCLEvent[] waitList) {
        cl_event[] events = toCLEventArray(waitList);
        cl_event event = new cl_event();
        clEnqueueWriteBuffer(
                commandQueue, memory.memory, false, 0, (long) Sizeof.cl_long * values.length, Pointer.to(values),
                events == null ? 0 : events.length, events, event
        );
        return new OpenCLEvent(event);
    }

    public OpenCLEvent enqueueWriteBufferAsync(OpenCLMemory.Float memory, float[] values, OpenCLEvent[] waitList) {
        cl_event[] events = toCLEventArray(waitList);
        cl_event event = new cl_event();
        clEnqueueWriteBuffer(
                commandQueue, memory.memory, false, 0, (long) Sizeof.cl_float * values.length, Pointer.to(values),
                events == null ? 0 : events.length, events, event
        );
        return new OpenCLEvent(event);
    }

    public OpenCLEvent enqueueWriteBufferAsync(OpenCLMemory.Double memory, double[] values, OpenCLEvent[] waitList) {
        cl_event[] events = toCLEventArray(waitList);
        cl_event event = new cl_event();
        clEnqueueWriteBuffer(
                commandQueue, memory.memory, false, 0, (long) Sizeof.cl_double * values.length, Pointer.to(values),
                events == null ? 0 : events.length, events, event
        );
        return new OpenCLEvent(event);
    }

    public void enqueueNDRangeKernel(OpenCLKernel kernel, Range range) {
        clEnqueueNDRangeKernel(commandQueue, kernel.kernel, range.workDim, range.globalWorkOffset, range.globalWorkSize, range.localWorkSize, 0, null, null);
    }

    public OpenCLEvent enqueueNDRangeKernel(OpenCLKernel kernel, Range range, OpenCLEvent[] waitList) {
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

        public static Range createND(int workDim, long[] globalWorkOffset, long[] globalWorkSize, long[] localWorkSize) {
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
