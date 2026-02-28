package io.github.sypiece.opencl;

import org.jocl.cl_device_id;
import org.jocl.cl_device_partition_property;

import static org.jocl.CL.clCreateSubDevices;

public class OpenCLSubDevice extends OpenCLDevice {
    public static OpenCLSubDevice[] create(OpenCLDevice parent, PartitionProperty partitionProperty) {
        clCreateSubDevices(parent, partitionProperty.partitionProperty);
    }

    OpenCLSubDevice(cl_device_id deviceID) {
        super(deviceID);
    }

    public static class PartitionProperty {
        final cl_device_partition_property partitionProperty = new cl_device_partition_property();

        public PartitionProperty() {}


    }
}
