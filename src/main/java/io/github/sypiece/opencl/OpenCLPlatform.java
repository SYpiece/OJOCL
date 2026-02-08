package io.github.sypiece.opencl;

import org.jocl.CL;
import org.jocl.cl_device_id;
import org.jocl.cl_platform_id;

import java.util.Arrays;

import static org.jocl.CL.*;

public class OpenCLPlatform extends OpenCLInfoObject<cl_platform_id> {
    final cl_platform_id platformID;

    OpenCLPlatform(cl_platform_id platformID) {
        super(platformID, CL::clGetPlatformInfo);
        this.platformID = platformID;
    }

    public String getName() {
        return getStringInfo(CL_PLATFORM_NAME);
    }

    public String getVendor() {
        return getStringInfo(CL_PLATFORM_VENDOR);
    }

    public String getProfile() {
        return getStringInfo(CL_PLATFORM_PROFILE);
    }

    public String getVersion() {
        return getStringInfo(CL_PLATFORM_VERSION);
    }

    public String[] getExtensions() {
        return getStringInfo(CL_PLATFORM_EXTENSIONS).split(" ");
    }

    public int getDeviceCount() {
        return getDeviceCount(OpenCLDevice.Type.ALL);
    }

    public int getDeviceCount(OpenCLDevice.Type deviceType) {
        int[] numDevices = new int[1];
        clGetDeviceIDs(platformID, deviceType.value, 0, null, numDevices);
        return numDevices[0];
    }

    public OpenCLDevice[] getDevices() {
        return getDevices(OpenCLDevice.Type.ALL);
    }

    public OpenCLDevice[] getDevices(OpenCLDevice.Type deviceType) {
        int numDevices = getDeviceCount(deviceType);
        cl_device_id[] devices = new cl_device_id[numDevices];
        clGetDeviceIDs(platformID, deviceType.value, numDevices, devices, null);
        OpenCLDevice[] openCLDevices = new OpenCLDevice[numDevices];
        for (int i = 0; i < numDevices; i++) {
            openCLDevices[i] = new OpenCLDevice(devices[i]);
        }
        return openCLDevices;
    }

    @Override
    public String toString() {
        return String.format(
                "OpenCLPlatform{" +
                        "name='%s', " +
                        "vendor='%s', " +
                        "profile='%s', " +
                        "version='%s', " +
                        "extensions='%s'" +
                "}",
                getName(), getVendor(), getProfile(), getVendor(), Arrays.toString(getExtensions()));
    }
}
