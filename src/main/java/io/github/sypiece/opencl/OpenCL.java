package io.github.sypiece.opencl;

import org.jocl.CL;
import org.jocl.cl_platform_id;

import static org.jocl.CL.CL_SUCCESS;
import static org.jocl.CL.clGetPlatformIDs;

public class OpenCL {
    static {
        CL.setExceptionsEnabled(true);
    }

    public static int getPlatformCount() {
        int[] numPlatforms = new int[1];
        int result = clGetPlatformIDs(0, null, numPlatforms);
        if (result != CL_SUCCESS) {
            throw new RuntimeException("Error: " + result);
        }
        return numPlatforms[0];
    }

    public static OpenCLPlatform[] getPlatforms() {
        int numPlatforms = getPlatformCount();
        cl_platform_id[] platforms = new cl_platform_id[numPlatforms];
        clGetPlatformIDs(numPlatforms, platforms, null);
        OpenCLPlatform[] openCLPlatforms = new OpenCLPlatform[numPlatforms];
        for (int i = 0; i < numPlatforms; i++) {
            openCLPlatforms[i] = new OpenCLPlatform(platforms[i]);
        }
        return openCLPlatforms;
    }

    protected OpenCL() {}
}
