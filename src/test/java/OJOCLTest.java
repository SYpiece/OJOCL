import io.github.sypiece.opencl.*;
import org.junit.Test;

import static org.junit.Assert.*;

import java.util.Random;

public class OJOCLTest {
    @Test
    public void testA() {
        final int ROWS = 64;
        final int COLS = 64;
        final int SIZE = ROWS * COLS;

        float[] a = new float[SIZE];
        float[] b = new float[SIZE];
        float[] c = new float[SIZE];

        Random random = new Random();
        for (int i = 0; i < SIZE; i++) {
            a[i] = random.nextFloat();
            b[i] = random.nextFloat();
        }

        String kernelSource =
                "__kernel void matrixAdd(__global const float* a, " +
                        "                     __global const float* b, " +
                        "                     __global float* result, " +
                        "                     const int size) { " +
                        "    int gid = get_global_id(0); " +
                        "        result[gid] = a[gid] + b[gid]; " +
                        "}";

        OpenCLDevice device = OpenCL.getPlatforms()[0].getDevices(OpenCLDevice.Type.GPU)[0];

        OpenCLMemory.Flags argsFlags = new OpenCLMemory.Flags().setReadOnly().setCopyHostPtr();
        OpenCLMemory.Flags resultFlags = new OpenCLMemory.Flags().setWriteOnly();

        try (
                OpenCLContext context = OpenCLContext.create(device);
                OpenCLCommandQueue queue = OpenCLCommandQueue.create(context, device);
                OpenCLMemory.Float aBuffer = OpenCLMemory.createFloatBuffer(context, argsFlags, a);
                OpenCLMemory.Float bBuffer = OpenCLMemory.createFloatBuffer(context, argsFlags, b);
                OpenCLMemory.Float resultBuffer = OpenCLMemory.createFloatBuffer(context, resultFlags, SIZE);

                OpenCLProgram program = OpenCLProgram.create(context, kernelSource);
                OpenCLKernel kernel = program.build().createKernel("matrixAdd");
        ) {
            kernel.setArg(0, aBuffer);
            kernel.setArg(1, bBuffer);
            kernel.setArg(2, resultBuffer);
            kernel.setArg(3, SIZE);

            queue.enqueueNDRangeKernel(kernel, OpenCLCommandQueue.Range.create(SIZE));

            queue.enqueueReadBuffer(resultBuffer, c);
        }

        for (int i = 0; i < SIZE; i++) {
            float cpuResult = a[i] + b[i];
            assertTrue(Math.abs(cpuResult - c[i]) < 0.001);
        }
    }
}
