package io.github.sypiece.opencl;

import org.jocl.CL;
import org.jocl.cl_device_id;

import static org.jocl.CL.*;

public class OpenCLDevice extends OpenCLObject<cl_device_id> {
    final cl_device_id deviceID;

    OpenCLDevice(cl_device_id deviceID) {
        super(deviceID, CL::clGetDeviceInfo);
        this.deviceID = deviceID;
    }

    public int getAddressBits() {
        return getIntInfo(CL_DEVICE_ADDRESS_BITS);
    }

    public boolean isAvailable() {
        return getBooleanInfo(CL_DEVICE_AVAILABLE);
    }

    public boolean isCompilerAvailable() {
        return getBooleanInfo(CL_DEVICE_COMPILER_AVAILABLE);
    }

    public DoubleFPConfig getDoubleFPConfig() {
        return new DoubleFPConfig(getLongInfo(CL_DEVICE_DOUBLE_FP_CONFIG));
    }

    public boolean isEndianLittle() {
        return getBooleanInfo(CL_DEVICE_ENDIAN_LITTLE);
    }

    public boolean isErrorCorrectionSupport() {
        return getBooleanInfo(CL_DEVICE_ERROR_CORRECTION_SUPPORT);
    }

    public ExecutionCapabilities getExecutionCapabilities() {
        return new ExecutionCapabilities(getLongInfo(CL_DEVICE_EXECUTION_CAPABILITIES));
    }

    public String[] getExtensions() {
        return getStringInfo(CL_DEVICE_EXTENSIONS).split(" ");
    }

    public long getGlobalMemCacheSize() {
        return getLongInfo(CL_DEVICE_GLOBAL_MEM_CACHE_SIZE);
    }

    public GlobalMemoryCacheType getGlobalMemoryCacheType() {
        return new GlobalMemoryCacheType(getIntInfo(CL_DEVICE_GLOBAL_MEM_CACHE_TYPE));
    }

    public int getGlobalMemCacheLineSize() {
        return getIntInfo(CL_DEVICE_GLOBAL_MEM_CACHELINE_SIZE);
    }

    public long getGlobalMemSize() {
        return getLongInfo(CL_DEVICE_GLOBAL_MEM_SIZE);
    }

    public HalfFPConfig getHalfFPConfig() {
        return new HalfFPConfig(getLongInfo(CL_DEVICE_HALF_FP_CONFIG));
    }

    @Deprecated
    public boolean isHostUnifiedMemory() {
        return getBooleanInfo(CL_DEVICE_HOST_UNIFIED_MEMORY);
    }

    public boolean isImageSupport() {
        return getBooleanInfo(CL_DEVICE_IMAGE_SUPPORT);
    }

    public long getImage2DMaxHeight() {
        return getSizeTInfo(CL_DEVICE_IMAGE2D_MAX_HEIGHT);
    }

    public long getImage2DMaxWidth() {
        return getSizeTInfo(CL_DEVICE_IMAGE2D_MAX_WIDTH);
    }

    public long getImage3DMaxDepth() {
        return getSizeTInfo(CL_DEVICE_IMAGE3D_MAX_DEPTH);
    }

    public long getImage3DMaxHeight() {
        return getSizeTInfo(CL_DEVICE_IMAGE3D_MAX_HEIGHT);
    }

    public long getImage3DMaxWidth() {
        return getSizeTInfo(CL_DEVICE_IMAGE3D_MAX_WIDTH);
    }

    public long getLocalMemSize() {
        return getLongInfo(CL_DEVICE_LOCAL_MEM_SIZE);
    }

    public LocalMemoryType getLocalMemoryType() {
        return new LocalMemoryType(getIntInfo(CL_DEVICE_LOCAL_MEM_TYPE));
    }

    public int getMaxClockFrequency() {
        return getIntInfo(CL_DEVICE_MAX_CLOCK_FREQUENCY);
    }

    public int getMaxComputeUnits() {
        return getIntInfo(CL_DEVICE_MAX_COMPUTE_UNITS);
    }

    public int getMaxConstantArgs() {
        return getIntInfo(CL_DEVICE_MAX_CONSTANT_ARGS);
    }

    public long getMaxConstantBufferSize() {
        return getLongInfo(CL_DEVICE_MAX_CONSTANT_BUFFER_SIZE);
    }

    public long getMaxMemAllocSize() {
        return getLongInfo(CL_DEVICE_MAX_MEM_ALLOC_SIZE);
    }

    public long getMaxParameterSize() {
        return getSizeTInfo(CL_DEVICE_MAX_PARAMETER_SIZE);
    }

    public int getMaxReadImageArgs() {
        return getIntInfo(CL_DEVICE_MAX_READ_IMAGE_ARGS);
    }

    public int getMaxSamples() {
        return getIntInfo(CL_DEVICE_MAX_SAMPLERS);
    }

    public long getMaxWorkGroupSize() {
        return getSizeTInfo(CL_DEVICE_MAX_WORK_GROUP_SIZE);
    }

    public int getMaxWorkItemDimensions() {
        return getIntInfo(CL_DEVICE_MAX_WORK_ITEM_DIMENSIONS);
    }

    public long[] getMaxWorkItemSizes() {
        return getSizeTArrayInfo(CL_DEVICE_MAX_WORK_ITEM_SIZES);
    }

    public int getMaxWriteImageArgs() {
        return getIntInfo(CL_DEVICE_MAX_WRITE_IMAGE_ARGS);
    }

    public int getMemBaseAddrAlign() {
        return getIntInfo(CL_DEVICE_MEM_BASE_ADDR_ALIGN);
    }

    public int getMinDataTypeAlignSize() {
        return getIntInfo(CL_DEVICE_MIN_DATA_TYPE_ALIGN_SIZE);
    }

    public String getName() {
        return getStringInfo(CL_DEVICE_NAME);
    }

    public int getNativeVectorWidthChar() {
        return getIntInfo(CL_DEVICE_NATIVE_VECTOR_WIDTH_CHAR);
    }

    public int getNativeVectorWidthShort() {
        return getIntInfo(CL_DEVICE_NATIVE_VECTOR_WIDTH_SHORT);
    }

    public int getNativeVectorWidthInt() {
        return getIntInfo(CL_DEVICE_NATIVE_VECTOR_WIDTH_INT);
    }

    public int getNativeVectorWidthLong() {
        return getIntInfo(CL_DEVICE_NATIVE_VECTOR_WIDTH_LONG);
    }

    public int getNativeVectorWidthFloat() {
        return getIntInfo(CL_DEVICE_NATIVE_VECTOR_WIDTH_FLOAT);
    }

    public int getNativeVectorWidthDouble() {
        return getIntInfo(CL_DEVICE_NATIVE_VECTOR_WIDTH_DOUBLE);
    }

    public int getNativeVectorWidthHalf() {
        return getIntInfo(CL_DEVICE_NATIVE_VECTOR_WIDTH_HALF);
    }

    public String getOpenCLCVersion() {
        return getStringInfo(CL_DEVICE_OPENCL_C_VERSION);
    }

    public OpenCLPlatform getPlatform() {
        return getPlatformInfo(CL_DEVICE_PLATFORM);
    }

    public int getPreferredVectorWidthChar() {
        return getIntInfo(CL_DEVICE_PREFERRED_VECTOR_WIDTH_CHAR);
    }

    public int getPreferredVectorWidthShort() {
        return getIntInfo(CL_DEVICE_PREFERRED_VECTOR_WIDTH_SHORT);
    }

    public int getPreferredVectorWidthInt() {
        return getIntInfo(CL_DEVICE_PREFERRED_VECTOR_WIDTH_INT);
    }

    public int getPreferredVectorWidthLong() {
        return getIntInfo(CL_DEVICE_PREFERRED_VECTOR_WIDTH_LONG);
    }

    public int getPreferredVectorWidthFloat() {
        return getIntInfo(CL_DEVICE_PREFERRED_VECTOR_WIDTH_FLOAT);
    }

    public int getPreferredVectorWidthDouble() {
        return getIntInfo(CL_DEVICE_PREFERRED_VECTOR_WIDTH_DOUBLE);
    }

    public int getPreferredVectorWidthHalf() {
        return getIntInfo(CL_DEVICE_PREFERRED_VECTOR_WIDTH_HALF);
    }

    public String getProfile() {
        return getStringInfo(CL_DEVICE_PROFILE);
    }

    @Deprecated
    public QueueProperties getQueueProperties() {
        return new QueueProperties(getLongInfo(CL_DEVICE_QUEUE_PROPERTIES));
    }

    public SingleFPConfig getSingleFPConfig() {
        return new SingleFPConfig(getLongInfo(CL_DEVICE_SINGLE_FP_CONFIG));
    }

    public Type getType() {
        return Type.valueOf(getLongInfo(CL_DEVICE_TYPE));
    }

    public String getVendor() {
        return getStringInfo(CL_DEVICE_VENDOR);
    }

    public int getVendorID() {
        return getIntInfo(CL_DEVICE_VENDOR_ID);
    }

    public String getVersion() {
        return getStringInfo(CL_DEVICE_VERSION);
    }

    public String getDriverVersion() {
        return getStringInfo(CL_DRIVER_VERSION);
    }

    public enum Type {
        ALL(CL_DEVICE_TYPE_ALL),
        CPU(CL_DEVICE_TYPE_CPU),
        GPU(CL_DEVICE_TYPE_GPU),
        ACCELERATOR(CL_DEVICE_TYPE_ACCELERATOR),
        CUSTOM(CL_DEVICE_TYPE_CUSTOM),
        DEFAULT(CL_DEVICE_TYPE_DEFAULT);

        public final long value;

        Type(long value) {
            this.value = value;
        }

        public static Type valueOf(long value) {
            for (Type type : Type.values()) {
                if (type.value == value) {
                    return type;
                }
            }
            throw new RuntimeException("Invalid value");
        }
    }

    public static class DoubleFPConfig {
        public static final long
                DENORM = CL_FP_DENORM,
                INF_NAN = CL_FP_INF_NAN,
                ROUND_TO_NEAREST = CL_FP_ROUND_TO_NEAREST,
                ROUND_TO_ZERO = CL_FP_ROUND_TO_ZERO,
                ROUND_TO_INF = CL_FP_ROUND_TO_INF,
                FMA = CL_FP_FMA;

        public final long value;

        public boolean supportDenorm() {
            return (value & DENORM) != 0;
        }

        public boolean supportInfNan() {
            return (value & INF_NAN) != 0;
        }

        public boolean supportRoundToNearest() {
            return (value & ROUND_TO_NEAREST) != 0;
        }

        public boolean supportRoundToZero() {
            return (value & ROUND_TO_ZERO) != 0;
        }

        public boolean supportRoundToInf() {
            return (value & ROUND_TO_INF) != 0;
        }

        public boolean supportFMA() {
            return (value & FMA) != 0;
        }

        public boolean support(long flag) {
            return (value & flag) != 0;
        }

        DoubleFPConfig(long value) {
            this.value = value;
        }
    }

    public static class ExecutionCapabilities {
        public static final long
                EXEC_KERNEL = CL_EXEC_KERNEL,
                EXEC_NATIVE_KERNEL = CL_EXEC_NATIVE_KERNEL;

        public final long value;

        ExecutionCapabilities(long value) {
            this.value = value;
        }

        public boolean supportKernel() {
            return (value & EXEC_KERNEL) != 0;
        }

        public boolean supportNativeKernel() {
            return (value & EXEC_NATIVE_KERNEL) != 0;
        }

        public boolean support(long flag) {
            return (value & flag) != 0;
        }
    }

    public static class GlobalMemoryCacheType {
        public static final int
                NONE = CL_NONE,
                READ_ONLY_CACHE = CL_READ_ONLY_CACHE,
                READ_WRITE_CACHE = CL_READ_WRITE_CACHE;

        public final int value;

        GlobalMemoryCacheType(int value) {
            this.value = value;
        }

        public boolean isNone() {
            return value == NONE;
        }

        public boolean isReadOnlyCache() {
            return value == READ_ONLY_CACHE;
        }

        public boolean isReadWriteCache() {
            return value == READ_WRITE_CACHE;
        }

        public boolean is(int flag) {
            return value == flag;
        }
    }

    public static class HalfFPConfig {
        public static final long
                DENORM = CL_FP_DENORM,
                INF_NAN = CL_FP_INF_NAN,
                ROUND_TO_NEAREST = CL_FP_ROUND_TO_NEAREST,
                ROUND_TO_ZERO = CL_FP_ROUND_TO_ZERO,
                ROUND_TO_INF = CL_FP_ROUND_TO_INF,
                FMA = CL_FP_FMA,
                SOFT_FLOAT = CL_FP_SOFT_FLOAT;

        public final long value;

        HalfFPConfig(long value) {
            this.value = value;
        }

        public boolean supportDenorm() {
            return (value & DENORM) != 0;
        }

        public boolean supportInfNan() {
            return (value & INF_NAN) != 0;
        }

        public boolean supportRoundToNearest() {
            return (value & ROUND_TO_NEAREST) != 0;
        }

        public boolean supportRoundToZero() {
            return (value & ROUND_TO_ZERO) != 0;
        }

        public boolean supportRoundToInf() {
            return (value & ROUND_TO_INF) != 0;
        }

        public boolean supportFMA() {
            return (value & FMA) != 0;
        }

        public boolean supportSoftFloat() {
            return (value & SOFT_FLOAT) != 0;
        }

        public boolean support(long flag) {
            return (value & flag) != 0;
        }
    }

    public static class LocalMemoryType {
        public static final int
                LOCAL = CL_LOCAL,
                GLOBAL = CL_GLOBAL;

        public final int value;

        LocalMemoryType(int value) {
            this.value = value;
        }

        public boolean isLocal() {
            return value == LOCAL;
        }

        public boolean isGlobal() {
            return value == GLOBAL;
        }

        public boolean is(int flag) {
            return value == flag;
        }
    }

    public static class QueueProperties {
        public static final long
                OUT_OF_ORDER_EXEC_MODE_ENABLE = CL_QUEUE_OUT_OF_ORDER_EXEC_MODE_ENABLE,
                PROFILING_ENABLE = CL_QUEUE_PROFILING_ENABLE;

        public final long value;

        QueueProperties(long value) {
            this.value = value;
        }

        public boolean supportOutOfOrderExecMode() {
            return (value & OUT_OF_ORDER_EXEC_MODE_ENABLE) != 0;
        }

        public boolean supportProfiling() {
            return (value & PROFILING_ENABLE) != 0;
        }

        public boolean support(long flag) {
            return (value & flag) != 0;
        }
    }

    public static class SingleFPConfig {
        public static final long
                DENORM = CL_FP_DENORM,
                INF_NAN = CL_FP_INF_NAN,
                ROUND_TO_NEAREST = CL_FP_ROUND_TO_NEAREST,
                ROUND_TO_ZERO = CL_FP_ROUND_TO_ZERO,
                ROUND_TO_INF = CL_FP_ROUND_TO_INF,
                FMA = CL_FP_FMA,
                SOFT_FLOAT = CL_FP_SOFT_FLOAT;

        public final long value;

        SingleFPConfig(long value) {
            this.value = value;
        }

        public boolean supportDenorm() {
            return (value & DENORM) != 0;
        }

        public boolean supportInfNan() {
            return (value & INF_NAN) != 0;
        }

        public boolean supportRoundToNearest() {
            return (value & ROUND_TO_NEAREST) != 0;
        }

        public boolean supportRoundToZero() {
            return (value & ROUND_TO_ZERO) != 0;
        }

        public boolean supportRoundToInf() {
            return (value & ROUND_TO_INF) != 0;
        }

        public boolean supportFMA() {
            return (value & FMA) != 0;
        }

        public boolean supportSoftFloat() {
            return (value & SOFT_FLOAT) != 0;
        }

        public boolean support(long flag) {
            return (value & flag) != 0;
        }
    }
}
