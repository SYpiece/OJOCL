package io.github.sypiece.opencl;

import org.jocl.CL;
import org.jocl.cl_context;
import org.jocl.cl_context_properties;
import org.jocl.cl_device_id;

import static org.jocl.CL.*;

public class OpenCLContext extends OpenCLBaseObject<cl_context> implements AutoCloseable {
    public static OpenCLContext create(OpenCLDevice... devices) {
        return create(null,  devices);
    }

    public static OpenCLContext create(Properties properties, OpenCLDevice... devices) {
        cl_device_id[] deviceIDs = new cl_device_id[devices.length];
        for (int i = 0; i < devices.length; i++) {
            deviceIDs[i] = devices[i].deviceID;
        }
        return new OpenCLContext(clCreateContext(properties == null ? null : properties.properties, deviceIDs.length, deviceIDs, null, null, null));
    }

    final cl_context context;

    OpenCLContext(cl_context context) {
        super(context, CL::clGetContextInfo);
        this.context = context;
    }

    public int getReferenceCount() {
        return getIntInfo(CL_CONTEXT_REFERENCE_COUNT);
    }

    public int getNumDevices() {
        return getIntInfo(CL_CONTEXT_NUM_DEVICES);
    }

    public OpenCLDevice[] getDevices() {
        return getDeviceArrayInfo(CL_CONTEXT_DEVICES);
    }

    @Override
    public void close() {
        clReleaseContext(context);
    }

    public static class Properties {
        public static final int
                PLATFORM = CL_CONTEXT_PLATFORM,
                GL_CONTEXT_HDR = CL_GL_CONTEXT_KHR,
                EGL_DISPLAY_HDR = CL_EGL_DISPLAY_KHR,
                GLX_DISPLAY_KHR = CL_GLX_DISPLAY_KHR,
                WGL_HDC_KHR = CL_WGL_HDC_KHR,
                CGL_SHARE_GROUP_KHR = CL_CGL_SHAREGROUP_KHR;

        final cl_context_properties properties = new cl_context_properties();

        public Properties() {}

        public Properties addPlatform(OpenCLPlatform value) {
            properties.addProperty(CL_CONTEXT_PLATFORM, value.baseObject);
            return this;
        }

        public Properties add(int property, OpenCLPlatform value) {
            properties.addProperty(property, value.baseObject);
            return this;
        }

        public Properties add(int property, long value) {
            properties.addProperty(property, value);
            return this;
        }
    }
}
