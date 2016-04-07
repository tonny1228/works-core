/**
 * 
 */
package works.tonny.apps.deploy;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.lang.management.ClassLoadingMXBean;
import java.lang.management.CompilationMXBean;
import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.lang.management.OperatingSystemMXBean;
import java.lang.management.RuntimeMXBean;
import java.lang.management.ThreadMXBean;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Date;
import java.util.StringTokenizer;

import org.apache.commons.lang3.math.NumberUtils;
import org.llama.library.utils.Formatter;

/**
 * @author 祥栋
 */
public class SystemInfo {

	private String linuxVersion;
	private OperatingSystemMXBean operatingSystemMXBean;
	// private HotspotThreadMBean hotspotThreadMBean;
	private ClassLoadingMXBean classLoadingMXBean;
	private CompilationMXBean compilationMXBean;
	// private HotspotClassLoadingMBean hotspotClassLoadingMBean;
	private RuntimeMXBean runtimeMXBean;
	private ThreadMXBean threadMXBean;
	private MemoryMXBean memoryMXBean;

	private static SystemInfo info = new SystemInfo();

	private SystemInfo() {
		init();
	}

	public static SystemInfo getInstance() {
		return info;
	}

	private void init() {
		operatingSystemMXBean = (OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();
		// hotspotThreadMBean = ManagementFactory.getHotspotThreadMBean();
		classLoadingMXBean = ManagementFactory.getClassLoadingMXBean();
		compilationMXBean = ManagementFactory.getCompilationMXBean();
		// hotspotClassLoadingMBean = ManagementFactory.getHotspotClassLoadingMBean();
		runtimeMXBean = ManagementFactory.getRuntimeMXBean();
		threadMXBean = ManagementFactory.getThreadMXBean();
		memoryMXBean = ManagementFactory.getMemoryMXBean();
		getFileEncoding();
	}

	/**
	 * 
	 */
	public String getFileEncoding() {
		return System.getProperty("file.encoding");
	}

	/**
	 * 
	 */
	public String getUserCountry() {
		return System.getProperty("user.country");
	}

	/**
	 * 
	 */
	public String getUserTimezone() {
		return System.getProperty("user.timezone");
	}

	/**
	 * 
	 */
	public String getUserName() {
		return System.getProperty("user.name");
	}

	/**
	 * 
	 */
	public String getUserLanguage() {
		return System.getProperty("user.language");
	}

	/**
	 * 
	 */
	public String getIP() {
		try {
			return InetAddress.getLocalHost().getHostAddress();
		} catch (UnknownHostException e) {
			return "unknown";
		}
	}

	public String getJavaVersion() {
		return System.getProperty("java.version");
	}

	/**
	 * heap size max
	 * 
	 * @return
	 */
	public String getHeapMemoryMax() {
		return Formatter.formatByte(memoryMXBean.getHeapMemoryUsage().getMax());
	}

	/**
	 * heap size used
	 * 
	 * @return
	 */
	public String getHeapMemoryUsed() {
		return Formatter.formatByte(memoryMXBean.getHeapMemoryUsage().getUsed());
	}

	/**
	 * @return
	 */
	public long getTotalStartedThreadCount() {
		return threadMXBean.getTotalStartedThreadCount();
	}

	/**
	 * @return
	 */
	public int getThreadCount() {
		return threadMXBean.getThreadCount();
	}

	/**
	 * 线程峰值
	 * 
	 * @return
	 */
	public int getPeakThreadCount() {
		return threadMXBean.getPeakThreadCount();
	}

	/**
	 * @return
	 */
	public int getDaemonThreadCount() {
		return threadMXBean.getDaemonThreadCount();
	}

	/**
	 * 启动时长
	 * 
	 * @return
	 */
	public String getUptime() {
		return runtimeMXBean.getUptime() / 1000 / 60 + "分钟";
	}

	/**
	 * 启动时间
	 * 
	 * @return
	 */
	public Date getStartTime() {
		return new Date(runtimeMXBean.getStartTime());
	}

	/**
	 * @return
	 */
	public String getLibraryPath() {
		return runtimeMXBean.getLibraryPath();
	}

	/**
	 * @return
	 */
	public String getClassPath() {
		return runtimeMXBean.getClassPath();
	}

	/**
	 * @return
	 */
	public String getBootClassPath() {
		return runtimeMXBean.getBootClassPath();
	}

	/**
	 * @return
	 */
	public String getLoadedClassSize() {
		return "";// Formatter.formatByte(hotspotClassLoadingMBean.getLoadedClassSize());
	}

	/**
	 * @return
	 */
	public long getInitializedClassCount() {
		return 0;// hotspotClassLoadingMBean.getInitializedClassCount();
	}

	/**
	 * @return
	 */
	public long getClassLoadingTime() {
		return 0;// hotspotClassLoadingMBean.getClassLoadingTime();
	}

	/**
	 * @return
	 */
	public String getCompilationName() {
		return compilationMXBean.getName();
	}

	/**
	 * @return
	 */
	public long getTotalLoadedClassCount() {
		return classLoadingMXBean.getTotalLoadedClassCount();
	}

	/**
	 * @return
	 */
	public int getLoadedClassCount() {
		return classLoadingMXBean.getLoadedClassCount();
	}

	/**
	 * @return
	 */
	public int getInternalThreadCount() {
		return 0;// hotspotThreadMBean.getInternalThreadCount();
	}

	/**
	 * @return
	 */
	public String getOSVersion() {
		return operatingSystemMXBean.getVersion() + System.getProperty("sun.os.patch.level");
	}

	/**
	 * @return
	 */
	public double getSystemLoadAverage() {
		return getOSName().toLowerCase().indexOf("windows") >= 0 ? getCpuRatioForWindows() : getCpuRateForLinux();
	}

	/**
	 * @return
	 */
	public String getOSName() {
		return operatingSystemMXBean.getName();
	}

	/**
	 * @return
	 */
	public String getFreePhysicalMemorySize() {
		return "";// Formatter.formatByte(operatingSystemMXBean.getFreePhysicalMemorySize());
	}

	/**
	 * @return
	 */
	public String getTotalPhysicalMemorySize() {
		return "";// Formatter.formatByte(operatingSystemMXBean.getTotalPhysicalMemorySize());
	}

	/**
	 * @return
	 */
	public String getCommittedVirtualMemorySize() {
		return "";// Formatter.formatByte(operatingSystemMXBean.getCommittedVirtualMemorySize());
	}

	/**
	 * @param operatingSystemMXBean
	 * @return
	 */
	public int getAvailableProcessors() {
		return operatingSystemMXBean.getAvailableProcessors();
	}

	/**
	 * @param operatingSystemMXBean
	 * @return
	 */
	public String getOSArch() {
		return operatingSystemMXBean.getArch();
	}

	private double getCpuRateForLinux() {
		InputStream is = null;
		InputStreamReader isr = null;
		BufferedReader brStat = null;
		StringTokenizer tokenStat = null;
		try {
			Process process = Runtime.getRuntime().exec("top -b -n 1");
			is = process.getInputStream();
			isr = new InputStreamReader(is);
			brStat = new BufferedReader(isr);

			if (linuxVersion != null && linuxVersion.equals("2.4")) {
				brStat.readLine();
				brStat.readLine();
				brStat.readLine();
				brStat.readLine();

				tokenStat = new StringTokenizer(brStat.readLine());
				tokenStat.nextToken();
				tokenStat.nextToken();
				String user = tokenStat.nextToken();
				tokenStat.nextToken();
				String system = tokenStat.nextToken();
				tokenStat.nextToken();
				String nice = tokenStat.nextToken();


				user = user.substring(0, user.indexOf("%"));
				system = system.substring(0, system.indexOf("%"));
				nice = nice.substring(0, nice.indexOf("%"));

				float userUsage = new Float(user).floatValue();
				float systemUsage = new Float(system).floatValue();
				float niceUsage = new Float(nice).floatValue();

				return (userUsage + systemUsage + niceUsage) / 100;
			} else {
				brStat.readLine();
				brStat.readLine();

				tokenStat = new StringTokenizer(brStat.readLine());
				tokenStat.nextToken();
				tokenStat.nextToken();
				tokenStat.nextToken();
				tokenStat.nextToken();
				tokenStat.nextToken();
				tokenStat.nextToken();
				tokenStat.nextToken();
				String cpuUsage = tokenStat.nextToken();

				Float usage = new Float(cpuUsage.substring(0, cpuUsage.indexOf("%")));

				return (1 - usage.floatValue() / 100);
			}

		} catch (IOException ioe) {
			freeResource(is, isr, brStat);
			return 1;
		} finally {
			freeResource(is, isr, brStat);
		}

	}

	private void freeResource(InputStream is, InputStreamReader isr, BufferedReader br) {
		try {
			if (is != null)
				is.close();
			if (isr != null)
				isr.close();
			if (br != null)
				br.close();
		} catch (IOException ioe) {
		}
	}

	/**
	 * 获得CPU使用率.
	 * 
	 * @return 返回cpu使用率
	 * @author GuoHuang
	 */
	private double getCpuRatioForWindows() {
		try {
			String procCmd = System.getenv("windir") + "\\system32\\wbem\\wmic.exe process get Caption,CommandLine,"
					+ "KernelModeTime,ReadOperationCount,ThreadCount,UserModeTime,WriteOperationCount";
			// 取进程信息
			long[] c0 = readCpu(Runtime.getRuntime().exec(procCmd));
			Thread.sleep(30);
			long[] c1 = readCpu(Runtime.getRuntime().exec(procCmd));
			if (c0 != null && c1 != null) {
				long idletime = c1[0] - c0[0];
				long busytime = c1[1] - c0[1];
				return Double.valueOf(100 * (busytime) / (busytime + idletime)).doubleValue();
			} else {
				return 0.0;
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			return 0.0;
		}
	}

	public static String substring(String src, int start_idx, int end_idx) {
		byte[] b = src.getBytes();
		String tgt = "";
		for (int i = start_idx; i <= end_idx; i++) {
			tgt += (char) b[i];
		}
		return tgt;
	}

	/**
	 * 读取CPU信息.
	 * 
	 * @param proc
	 * @return
	 * @author GuoHuang
	 */
	private long[] readCpu(final Process proc) {
		long[] retn = new long[2];
		try {
			proc.getOutputStream().close();
			InputStreamReader ir = new InputStreamReader(proc.getInputStream());
			LineNumberReader input = new LineNumberReader(ir);
			String line = input.readLine();
			if (line == null || line.length() < 10) {
				return null;
			}
			int capidx = line.indexOf("Caption");
			int cmdidx = line.indexOf("CommandLine");
			int rocidx = line.indexOf("ReadOperationCount");
			int umtidx = line.indexOf("UserModeTime");
			int kmtidx = line.indexOf("KernelModeTime");
			int wocidx = line.indexOf("WriteOperationCount");
			long idletime = 0;
			long kneltime = 0;
			long usertime = 0;
			while ((line = input.readLine()) != null) {
				if (line.length() < wocidx) {
					continue;
				}
				// 字段出现顺序：Caption,CommandLine,KernelModeTime,ReadOperationCount,
				// ThreadCount,UserModeTime,WriteOperation
				String caption = substring(line, capidx, cmdidx - 1).trim();
				String cmd = substring(line, cmdidx, kmtidx - 1).trim();
				if (cmd.indexOf("wmic.exe") >= 0) {
					continue;
				}
				// log.info("line="+line);
				if (caption.equals("System Idle Process") || caption.equals("System")) {
					idletime += Long.valueOf(substring(line, kmtidx, rocidx - 1).trim()).longValue();
					idletime += Long.valueOf(substring(line, umtidx, wocidx - 1).trim()).longValue();
					continue;
				}

				kneltime += NumberUtils.toLong(substring(line, kmtidx, rocidx - 1).trim());
				usertime += NumberUtils.toLong(substring(line, umtidx, wocidx - 1).trim());
			}
			retn[0] = idletime;
			retn[1] = kneltime + usertime;
			return retn;
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			try {
				proc.getInputStream().close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return null;
	}

}
