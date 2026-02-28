package io.github.sypiece.opencl;

import org.jocl.CL;
import org.jocl.cl_event;

import static org.jocl.CL.*;

public class OpenCLEvent extends OpenCLObject<cl_event> implements AutoCloseable {
    public static void waitFor(OpenCLEvent... events) {
        cl_event[] eventArray = new cl_event[events.length];
        for (int i = 0; i < events.length; i++) {
            eventArray[i] = events[i].event;
        }
        clWaitForEvents(eventArray.length, eventArray);
    }

    final cl_event event;

    public OpenCLEvent(cl_event event) {
        super(event, CL::clGetEventInfo, CL::clGetEventProfilingInfo);
        this.event = event;
    }

    public OpenCLCommandQueue getCommandQueue() {
        return getCommandQueueInfo(CL_EVENT_COMMAND_QUEUE);
    }

    public OpenCLContext getContext() {
        return getContextInfo(CL_EVENT_COMMAND_QUEUE);
    }

    public CommandType getCommandType() {
        return CommandType.valueOf(getIntInfo(CL_EVENT_COMMAND_TYPE));
    }

    public CommandExecutionStatus getCommandExecutionStatus() {
        return CommandExecutionStatus.valueOf(getIntInfo(CL_EVENT_COMMAND_EXECUTION_STATUS));
    }

    public int getReferenceCount() {
        return getIntInfo(CL_EVENT_REFERENCE_COUNT);
    }

    public long getProfilingCommandQueued() {
        return getLongInfo(CL_PROFILING_COMMAND_QUEUED, 1);
    }

    public long getProfilingCommandSubmit() {
        return getLongInfo(CL_PROFILING_COMMAND_SUBMIT, 1);
    }

    public long getProfilingCommandStart() {
        return getLongInfo(CL_PROFILING_COMMAND_START, 1);
    }

    public long getProfilingCommandEnd() {
        return getLongInfo(CL_PROFILING_COMMAND_END, 1);
    }

    public OpenCLEvent waitFor() {
        clWaitForEvents(1, new cl_event[]{event});
        return this;
    }

    public <T> OpenCLEvent setCallback(CommandExecutionStatus status, Callback<T> callback, T userData) {
        clSetEventCallback(event, status.value, (cl_event event, int command_exec_callback_type, Object user_data) -> {
            callback.call(this, status, userData);
        }, null);
        return this;
    }

    @Override
    public void close() {
        CL.clReleaseEvent(event);
    }

    public enum CommandType {
        ND_RANGE_KERNEL(CL_COMMAND_NDRANGE_KERNEL),
        TASK(CL_COMMAND_TASK),
        NATIVE_KERNEL(CL_COMMAND_NATIVE_KERNEL),
        READ_BUFFER(CL_COMMAND_READ_BUFFER),
        WRITE_BUFFER(CL_COMMAND_WRITE_BUFFER),
        COPY_BUFFER(CL_COMMAND_COPY_BUFFER),
        READ_IMAGE(CL_COMMAND_READ_IMAGE),
        WRITE_IMAGE(CL_COMMAND_WRITE_IMAGE),
        COPY_IMAGE(CL_COMMAND_COPY_IMAGE),
        COPY_BUFFER_TO_IMAGE(CL_COMMAND_COPY_BUFFER_TO_IMAGE),
        COPY_IMAGE_TO_BUFFER(CL_COMMAND_COPY_IMAGE_TO_BUFFER),
        MAP_BUFFER(CL_COMMAND_MAP_BUFFER),
        MAP_IMAGE(CL_COMMAND_MAP_IMAGE),
        UNMAP_MEM_OBJECT(CL_COMMAND_UNMAP_MEM_OBJECT),
        MARKER(CL_COMMAND_MARKER),
        ACQUIRE_GL_OBJECTS(CL_COMMAND_ACQUIRE_GL_OBJECTS),
        RELEASE_GL_OBJECTS(CL_COMMAND_RELEASE_GL_OBJECTS),
        READ_BUFFER_RECT(CL_COMMAND_READ_BUFFER_RECT),
        WRITE_BUFFER_RECT(CL_COMMAND_WRITE_BUFFER_RECT),
        COPY_BUFFER_RECT(CL_COMMAND_COPY_BUFFER_RECT),
        USER(CL_COMMAND_USER);

        public final int value;

        CommandType(int value) {
            this.value = value;
        }

        public static CommandType valueOf(int value) {
            for (CommandType type : values()) {
                if (type.value == value) {
                    return type;
                }
            }
            throw new IllegalArgumentException("Invalid command type value: " + value);
        }
    }

    public enum CommandExecutionStatus {
        QUEUED(CL_QUEUED),
        SUBMITTED(CL_SUBMITTED),
        RUNNING(CL_RUNNING),
        COMPLETE(CL_COMPLETE);

        public final int value;

        CommandExecutionStatus(int value) {
            this.value = value;
        }

        public static CommandExecutionStatus valueOf(int value) {
            for (CommandExecutionStatus status : values()) {
                if (status.value == value) {
                    return status;
                }
            }
            throw new IllegalArgumentException("Invalid command execution status value: " + value);
        }
    }

    public interface Callback<T> {
        void call(OpenCLEvent event, CommandExecutionStatus status, T userData);
    }
}
