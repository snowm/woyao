
import static com.sun.btrace.BTraceUtils.println;
import static com.sun.btrace.BTraceUtils.strcat;

import com.sun.btrace.BTraceUtils;
import com.sun.btrace.annotations.BTrace;
import com.sun.btrace.annotations.OnMethod;
import com.sun.btrace.annotations.ProbeClassName;
import com.sun.btrace.annotations.ProbeMethodName;

@BTrace
public class ExitTrace {

	@OnMethod(clazz = "java.lang.System", method = "exit")
	public static void traceSystemExit(@ProbeClassName String name, @ProbeMethodName String method) {
		println(strcat("System.exit().the class name=>", name));
		println(strcat("System.exit().the class method=>", method));
		BTraceUtils.jstack();
	}

}
