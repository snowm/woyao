package com.snowm.cache.jgroup;

import java.net.URL;

import org.apache.commons.lang3.StringUtils;
import org.kohsuke.args4j.Option;
import org.springframework.beans.factory.FactoryBean;

import net.sf.ehcache.CacheManager;

public class CacheManagerFactory implements FactoryBean<CacheManager> {

	@Option(name = "-cluster", required = true)
	private String clusterMembers;
	
	@Option(name = "-port", required = true)
	private String port;
	
	@Option(name = "-name", required = true)
	private String clusterName;
	
	@Option(name = "-ehcache", required = true)
	private String ehcacheConfigFile;
	
	@Option(name = "-jgroup")
	private String jgroupsConfigFile = "com/snowm/cache/jgroup/jgroups-tcp.xml";
	
	@Option(name = "-host")
	private String hostName;
	
	private String maxHeap;
	
	private String maxDisk;
	
	private CacheManager cacheManager;
	private final Object shutdownThreadLock = new Object();
	private Thread shutdownThread;

	@Override
	public CacheManager getObject() {
		if (this.cacheManager == null) {
			URL ehcacheUrl = this.validateProperties();
			this.setSystemProperties();
			this.cacheManager = new CacheManager(ehcacheUrl);
			this.setupShutdownProc(this.cacheManager);
		}
		return this.cacheManager;
	}

	@Override
	public Class<?> getObjectType() {
		return CacheManager.class;
	}

	@Override
	public boolean isSingleton() {
		return true;
	}

	private URL validateProperties() {
		this.checkNotNull(this.clusterMembers, "clusterMembers");
		this.checkNotNull(this.port, "port");
		this.checkNotNull(this.clusterName, "clusterName");
		this.checkValidResource(this.jgroupsConfigFile, "jgroupsConfigFile");
		return this.checkValidResource(this.ehcacheConfigFile, "ehcacheConfigFile");
	}

	private void setSystemProperties() {
		System.setProperty("distributed-cache.clusterMembers", this.clusterMembers);
		System.setProperty("distributed-cache.port", this.port);
		System.setProperty("distributed-cache.clusterName", this.clusterName);
		System.setProperty("distributed-cache.jgroupsConfigFile", this.jgroupsConfigFile);

		if (this.hostName != null) {
			System.setProperty("distributed-cache.hostName", this.hostName);
		}
		System.setProperty("distributed-cache.pool.heap.max", this.maxHeap);
		System.setProperty("distributed-cache.pool.disk.max", this.maxDisk);
	}

	private void checkNotNull(String value, String name) {
		if (value == null || value.isEmpty()) {
			throw new IllegalArgumentException(String.format("%s must be set", new Object[] { name }));
		}
	}

	private URL checkValidResource(String value, String name) {
		if (StringUtils.isEmpty(name)) {
		} else {
			throw new IllegalArgumentException(String.format("%s must be set", new Object[] { name }));
		}

		URL url = this.getClass().getClassLoader().getResource(value);
		if (url == null) {
			throw new IllegalArgumentException(String.format("%s is invalid", new Object[] { name }));
		} else {
			return url;
		}
	}

	private void setupShutdownProc(final CacheManager cacheManager) {
		synchronized (this.shutdownThreadLock) {
			Runnable shutdownProc = new Runnable() {

				@Override
				public void run() {
					cacheManager.shutdown();
				}
			};

			this.shutdownThread = new Thread(shutdownProc, String.format("%s-%s", new Object[] { this.clusterName, this.port }));
			Runtime.getRuntime().addShutdownHook(this.shutdownThread);
		}
	}

	public void shutdown() throws InterruptedException {
		synchronized (this.shutdownThreadLock) {
			Runtime.getRuntime().removeShutdownHook(this.shutdownThread);
			this.shutdownThread.start();
			this.shutdownThread.join();
			this.shutdownThread = null;
		}
	}

	public String getClusterMembers() {
		return this.clusterMembers;
	}

	public void setClusterMembers(String clusterMembers) {
		this.clusterMembers = clusterMembers;
	}

	public String getHostName() {
		return this.hostName;
	}

	public void setHostName(String hostName) {
		this.hostName = hostName;
	}

	public String getPort() {
		return this.port;
	}

	public void setPort(String port) {
		this.port = port;
	}

	public String getClusterName() {
		return this.clusterName;
	}

	public void setClusterName(String clusterName) {
		this.clusterName = clusterName;
	}

	public String getEhcacheConfigFile() {
		return this.ehcacheConfigFile;
	}

	public void setEhcacheConfigFile(String ehcacheConfigFile) {
		this.ehcacheConfigFile = ehcacheConfigFile;
	}

	public String getJgroupsConfigFile() {
		return this.jgroupsConfigFile;
	}

	public void setJgroupsConfigFile(String jgroupsConfigFile) {
		this.jgroupsConfigFile = jgroupsConfigFile;
	}

	public String getMaxHeap() {
		return maxHeap;
	}

	public void setMaxHeap(String maxHeap) {
		this.maxHeap = maxHeap;
	}

	public String getMaxDisk() {
		return maxDisk;
	}

	public void setMaxDisk(String maxDisk) {
		this.maxDisk = maxDisk;
	}
	
}
