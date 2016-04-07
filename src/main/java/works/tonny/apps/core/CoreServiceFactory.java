/**
 * <p>
 * 
 * </p>
 * <p>
 * Copyright: Copyright (c) tonny 2014
 * </p>
 * @date 2014-11-13 下午2:42:53
 * @author tonny
 */
package works.tonny.apps.core;

/**
 * <p>
 * 核心服务类工厂
 * </p>
 * 
 * @author tonny
 * @version 1.0.0
 */
public class CoreServiceFactory {
	private static CoreServiceFactory serviceFactory = new CoreServiceFactory();

	private CatalogService catalogService;

	private AttachReferenceService attachReferenceService;

	private AttachService attachService;

	private CatalogAuthService catalogAuthService;

	private DataDictionaryService dataDictionaryService;

	private ElementService elementService;

	private FormElementService formElementService;

	private FormService formService;

	private IDGeneratorService idGeneratorService;

	private LogService logService;

	private MailService mailService;

	private SettingService settingService;

	public static CoreServiceFactory getInstance() {
		return serviceFactory;
	}

	/**
	 * @return the catalogService
	 */
	public CatalogService getCatalogService() {
		return catalogService;
	}

	/**
	 * @param catalogService the catalogService to set
	 */
	void setCatalogService(CatalogService catalogService) {
		this.catalogService = catalogService;
	}

	/**
	 * @return the attachReferenceService
	 */
	public AttachReferenceService getAttachReferenceService() {
		return attachReferenceService;
	}

	/**
	 * @param attachReferenceService the attachReferenceService to set
	 */
	void setAttachReferenceService(AttachReferenceService attachReferenceService) {
		this.attachReferenceService = attachReferenceService;
	}

	/**
	 * @return the attachService
	 */
	public AttachService getAttachService() {
		return attachService;
	}

	/**
	 * @param attachService the attachService to set
	 */
	void setAttachService(AttachService attachService) {
		this.attachService = attachService;
	}

	/**
	 * @return the catalogAuthService
	 */
	public CatalogAuthService getCatalogAuthService() {
		return catalogAuthService;
	}

	/**
	 * @param catalogAuthService the catalogAuthService to set
	 */
	void setCatalogAuthService(CatalogAuthService catalogAuthService) {
		this.catalogAuthService = catalogAuthService;
	}

	/**
	 * @return the dataDictionaryService
	 */
	public DataDictionaryService getDataDictionaryService() {
		return dataDictionaryService;
	}

	/**
	 * @param dataDictionaryService the dataDictionaryService to set
	 */
	void setDataDictionaryService(DataDictionaryService dataDictionaryService) {
		this.dataDictionaryService = dataDictionaryService;
	}

	/**
	 * @return the elementService
	 */
	public ElementService getElementService() {
		return elementService;
	}

	/**
	 * @param elementService the elementService to set
	 */
	void setElementService(ElementService elementService) {
		this.elementService = elementService;
	}

	/**
	 * @return the formElementService
	 */
	public FormElementService getFormElementService() {
		return formElementService;
	}

	/**
	 * @param formElementService the formElementService to set
	 */
	void setFormElementService(FormElementService formElementService) {
		this.formElementService = formElementService;
	}

	/**
	 * @return the formService
	 */
	public FormService getFormService() {
		return formService;
	}

	/**
	 * @param formService the formService to set
	 */
	void setFormService(FormService formService) {
		this.formService = formService;
	}

	/**
	 * @return the idGeneratorService
	 */
	public IDGeneratorService getIdGeneratorService() {
		return idGeneratorService;
	}

	/**
	 * @param idGeneratorService the idGeneratorService to set
	 */
	void setIdGeneratorService(IDGeneratorService idGeneratorService) {
		this.idGeneratorService = idGeneratorService;
	}

	/**
	 * @return the logService
	 */
	public LogService getLogService() {
		return logService;
	}

	/**
	 * @param logService the logService to set
	 */
	void setLogService(LogService logService) {
		this.logService = logService;
	}

	/**
	 * @return the mailService
	 */
	public MailService getMailService() {
		return mailService;
	}

	/**
	 * @param mailService the mailService to set
	 */
	void setMailService(MailService mailService) {
		this.mailService = mailService;
	}

	public static class Helper {
		/**
		 * @param catalogService the catalogService to set
		 */
		public void setCatalogService(CatalogService catalogService) {
			CoreServiceFactory.getInstance().setCatalogService(catalogService);
		}

		/**
		 * @param attachReferenceService the attachReferenceService to set
		 */
		public void setAttachReferenceService(AttachReferenceService attachReferenceService) {
			CoreServiceFactory.getInstance().setAttachReferenceService(attachReferenceService);
		}

		/**
		 * @param attachService the attachService to set
		 */
		public void setAttachService(AttachService attachService) {
			CoreServiceFactory.getInstance().setAttachService(attachService);
		}

		/**
		 * @param catalogAuthService the catalogAuthService to set
		 */
		public void setCatalogAuthService(CatalogAuthService catalogAuthService) {
			CoreServiceFactory.getInstance().setCatalogAuthService(catalogAuthService);
		}

		/**
		 * @param dataDictionaryService the dataDictionaryService to set
		 */
		public void setDataDictionaryService(DataDictionaryService dataDictionaryService) {
			CoreServiceFactory.getInstance().setDataDictionaryService(dataDictionaryService);
		}

		/**
		 * @param elementService the elementService to set
		 */
		public void setElementService(ElementService elementService) {
			CoreServiceFactory.getInstance().setElementService(elementService);
		}

		/**
		 * @param formElementService the formElementService to set
		 */
		public void setFormElementService(FormElementService formElementService) {
			CoreServiceFactory.getInstance().setFormElementService(formElementService);
		}

		/**
		 * @param formService the formService to set
		 */
		public void setFormService(FormService formService) {
			CoreServiceFactory.getInstance().setFormService(formService);
		}

		/**
		 * @param idGeneratorService the idGeneratorService to set
		 */
		public void setIdGeneratorService(IDGeneratorService idGeneratorService) {
			CoreServiceFactory.getInstance().setIdGeneratorService(idGeneratorService);
		}

		/**
		 * @param logService the logService to set
		 */
		public void setLogService(LogService logService) {
			CoreServiceFactory.getInstance().setLogService(logService);
		}

		/**
		 * @param mailService the mailService to set
		 */
		public void setMailService(MailService mailService) {
			CoreServiceFactory.getInstance().setMailService(mailService);
		}

		/**
		 * @param mailService the mailService to set
		 */
		public void setSettingService(SettingService settingService) {
			CoreServiceFactory.getInstance().setSettingService(settingService);
		}

	}

	/**
	 * @return the settingService
	 */
	public SettingService getSettingService() {
		return settingService;
	}

	/**
	 * @param settingService the settingService to set
	 */
	public void setSettingService(SettingService settingService) {
		this.settingService = settingService;
	}
}