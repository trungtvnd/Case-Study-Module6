package codegym.com.vn.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

import java.util.concurrent.Executor;

@Component
public class ContextUtils {
	private static ApplicationContext applicationContext;

	@Autowired
	protected ContextUtils(ApplicationContext applicationContext) {
		ContextUtils.applicationContext = applicationContext;
	}

	public static <T> T getBean(Class<T> clazz) {
		return applicationContext.getBean(clazz);
	}

	public static Object getBean(String name) {
		return applicationContext.getBean(name);
	}

	public static <T> T getBean(String name, Class<T> clazz) {
		return applicationContext.getBean(name, clazz);
	}

	public static Executor getExecutor() {
		return getBean("asyncExecutor", Executor.class);
	}

	public static String getMessage(String code) {
		return ContextUtils.getMessage(code, new String[] {});
	}

	public static String getMessage(String code, String... args) {
		return getBean(MessageSource.class).getMessage(code, args,
				"Đã có lỗi xảy ra. Vui lòng liên hệ với quản trị viên", LocaleContextHolder.getLocale());
	}
}
