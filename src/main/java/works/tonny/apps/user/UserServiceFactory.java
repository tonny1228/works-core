/**
 * <p>
 * 
 * </p>
 * <p>
 * Copyright: Copyright (c) tonny 2014
 * </p>
 * @date 2014-11-13 下午3:01:07
 * @author tonny
 */
package works.tonny.apps.user;

import works.tonny.apps.user.service.*;

/**
 * <p>
 * 用户服务类工厂
 * </p>
 * 
 * @author tonny
 * @version 1.0.0
 */
public class UserServiceFactory {

	private static UserServiceFactory serviceFactory = new UserServiceFactory();

	AuthService authService;

	MemberService memberService;

	AuthEntityService authEntityService;

	CompanyService companyService;

	DepartmentService departmentService;

	DepartmentEntityService departmentEntityService;

	JobService jobService;

	PositionEntityService positionEntityService;

	PositionService positionService;

	RoleService roleService;

	RoleEntityService roleEntityService;

	TitleService titleService;

	UserEntityService userEntityService;

	UserService userService;

    OrgnizationHelper orgnizationHelper;

	private UserServiceFactory() {

	}

	public static UserServiceFactory getInstance() {
		return serviceFactory;
	}

	/**
	 * @return the authService
	 */
	public AuthService getAuthService() {
		return authService;
	}

	/**
	 * @return the memberService
	 */
	public MemberService getMemberService() {
		return memberService;
	}

	/**
	 * @return the authEntityService
	 */
	public AuthEntityService getAuthManageService() {
		return authEntityService;
	}

	/**
	 * @return the companyService
	 */
	public CompanyService getCompanyService() {
		return companyService;
	}

	/**
	 * @return the departmentService
	 */
	public DepartmentService getDepartmentService() {
		return departmentService;
	}

	/**
	 * @return the departmentEntityService
	 */
	public DepartmentEntityService getDepartmentManageService() {
		return departmentEntityService;
	}

	/**
	 * @return the jobService
	 */
	public JobService getJobService() {
		return jobService;
	}

	/**
	 * @return the positionEntityService
	 */
	public PositionEntityService getPositionManageService() {
		return positionEntityService;
	}

	/**
	 * @return the positionService
	 */
	public PositionService getPositionService() {
		return positionService;
	}

	/**
	 * @return the roleService
	 */
	public RoleService getRoleService() {
		return roleService;
	}

	/**
	 * @return the roleEntityService
	 */
	public RoleEntityService getRoleManageService() {
		return roleEntityService;
	}

	/**
	 * @return the titleService
	 */
	public TitleService getTitleService() {
		return titleService;
	}

	/**
	 * @return the userEntityService
	 */
	public UserEntityService getUserManageService() {
		return userEntityService;
	}

    public OrgnizationHelper getOrgnizationHelper() {
        return orgnizationHelper;
    }


    /**
	 * @return the userService
	 */
	public UserService getUserService() {
		return userService;
	}

	public static class Helper {
		/**
		 * @param authService the authService to set
		 */
		public void setAuthService(AuthService authService) {
			UserServiceFactory.getInstance().authService = authService;
		}

		/**
		 * @param memberService the memberService to set
		 */
		public void setMemberService(MemberService memberService) {
			UserServiceFactory.getInstance().memberService = memberService;
		}

		/**
		 * @param authEntityService the authEntityService to set
		 */
		public void setAuthEntityService(AuthEntityService authEntityService) {
			UserServiceFactory.getInstance().authEntityService = authEntityService;
		}

		/**
		 * @param companyService the companyService to set
		 */
		public void setCompanyService(CompanyService companyService) {
			UserServiceFactory.getInstance().companyService = companyService;
		}

		/**
		 * @param departmentService the departmentService to set
		 */
		public void setDepartmentService(DepartmentService departmentService) {
			UserServiceFactory.getInstance().departmentService = departmentService;
		}

		/**
		 * @param departmentEntityService the departmentEntityService to set
		 */
		public void setDepartmentEntityService(DepartmentEntityService departmentEntityService) {
			UserServiceFactory.getInstance().departmentEntityService = departmentEntityService;
		}

		/**
		 * @param jobService the jobService to set
		 */
		public void setJobService(JobService jobService) {
			UserServiceFactory.getInstance().jobService = jobService;
		}

		/**
		 * @param positionEntityService the positionEntityService to set
		 */
		public void setPositionEntityService(PositionEntityService positionEntityService) {
			UserServiceFactory.getInstance().positionEntityService = positionEntityService;
		}

		/**
		 * @param positionService the positionService to set
		 */
		public void setPositionService(PositionService positionService) {
			UserServiceFactory.getInstance().positionService = positionService;
		}

		/**
		 * @param roleService the roleService to set
		 */
		public void setRoleService(RoleService roleService) {
			UserServiceFactory.getInstance().roleService = roleService;
		}

		/**
		 * @param roleEntityService the roleEntityService to set
		 */
		public void setRoleEntityService(RoleEntityService roleEntityService) {
			UserServiceFactory.getInstance().roleEntityService = roleEntityService;
		}

		/**
		 * @param service
		 * @author tonny
		 */
		public void setTitleService(TitleService service) {
			UserServiceFactory.getInstance().titleService = service;
		}

		/**
		 * @param service
		 * @author tonny
		 */
		public void setUserService(UserService service) {
			UserServiceFactory.getInstance().userService = service;
		}

		public void setUserEntityService(UserEntityService service) {
			UserServiceFactory.getInstance().userEntityService = service;
		}


        public void setOrgnizationHelper(OrgnizationHelper orgnizationHelper) {
            UserServiceFactory.getInstance().orgnizationHelper = orgnizationHelper;
        }



	}

}
