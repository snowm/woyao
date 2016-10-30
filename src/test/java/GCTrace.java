
import static com.sun.btrace.BTraceUtils.println;
import static com.sun.btrace.BTraceUtils.strcat;

import com.sun.btrace.BTraceUtils;
import com.sun.btrace.annotations.BTrace;
import com.sun.btrace.annotations.OnMethod;
import com.sun.btrace.annotations.ProbeClassName;
import com.sun.btrace.annotations.ProbeMethodName;

@BTrace
public class GCTrace {

	@OnMethod(clazz = "java.lang.System", method = "gc")
	public static void traceSystemGC(@ProbeClassName String name, @ProbeMethodName String method) {
		println(strcat("systemgc.the class name=>", name));
		println(strcat("systemgc.the class method=>", method));
		BTraceUtils.jstack(20000);
	}

	@OnMethod(clazz = "java.lang.Runtime", method = "gc")
	public static void traceRuntimeGC(@ProbeClassName String name, @ProbeMethodName String method) {
		println(strcat("runtime.the class name=>", name));
		println(strcat("runtime.the class method=>", method));
		BTraceUtils.jstack(20000);
	}

	@OnMethod(clazz = "com.active.services.commerce.utils.FutureManager", method = "invokeAll")
	public static void traceFutureManager(@ProbeClassName String name, @ProbeMethodName String method) {
		BTraceUtils.jstack();
		println(strcat("Thread Count=>", BTraceUtils.str(BTraceUtils.threadCount())));
	}

}
