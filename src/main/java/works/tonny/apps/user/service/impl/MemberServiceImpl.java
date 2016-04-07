package works.tonny.apps.user.service.impl;

// Generated 2012-7-20 14:43:00 by Hibernate Tools 3.4.0.CR1

import java.util.Date;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang3.StringUtils;
import org.llama.library.cryptography.Encryptable;
import org.llama.library.utils.Assert;
import org.llama.library.utils.PagedList;
import org.llama.library.utils.PropertiesUtils;
import org.springframework.transaction.annotation.Transactional;

import works.tonny.apps.exception.DataException;
import works.tonny.apps.impl.AbstractCriteriaQuery;
import works.tonny.apps.support.BaseDAOSupport;
import works.tonny.apps.support.CallBeforeUpdate;
import works.tonny.apps.support.ListSupport;
import works.tonny.apps.support.message.MessageEvent;
import works.tonny.apps.support.message.MessageManager;
import works.tonny.apps.user.AuthedAbstractService;
import works.tonny.apps.user.Member;
import works.tonny.apps.user.MemberQuery;
import works.tonny.apps.user.dao.MemberDAO;
import works.tonny.apps.user.service.MemberEntityService;

/**
 * Service object for domain model class Member.
 *
 * @author Tonny Liu
 * @see entity.Member
 */
public class MemberServiceImpl extends AuthedAbstractService implements MemberEntityService {
    private MemberDAO memberDAO;
    private Encryptable encryptable;

    /**
     * 读取Member
     *
     * @param id 编号
     * @return Member
     */
    public Member get(String id) {
        Member member = memberDAO.get(id);
        return member;
    }

    /**
     * 创建Member
     *
     * @param member
     * @return
     */
    public String save(Member member) {
        if (getByUsername(member.getUsername()) != null) {
            throw new DataException("用户已存在");
        }
        validate(member);
        if (StringUtils.isNotEmpty(member.getPassword())) {
            member.setPassword(encryptable.encrypt(member.getPassword()));
        }
        String id = memberDAO.save(member);
        MessageManager.notify(Member.class, MessageEvent.CREATED, member);
        return id;
    }

    /**
     * 验证并初始数据
     */
    private void validate(Member member) {
        member.setRegTime(new Date());
        member.setUserId("member");
    }

    /*
     * (non-Javadoc)
     *
     * @see works.tonny.core.service.MemberService#score(int)
     */
    public void updateScore(String username, int score) {
        Member member = getByUsername(username);
        member.setScore(member.getScore() + score);
        update(member);
    }

    /**
     * 更新Member
     *
     * @param member
     */
    public void update(Member member) {
        update(member, new CallBeforeUpdate<Member>() {
            @Override
            public void execute(Member db, Member member) {
                PropertiesUtils.copyExcludesProperties(db, member, "id", "password", "status", "regTime", "userId",
                        "score", "no");
            }
        });
    }

    @Override
    public void update(Member member, CallBeforeUpdate<Member> call) {
        Member db = get(member.getId());
        call.execute(db, member);
        memberDAO.update(db);
        MessageManager.notify(Member.class, MessageEvent.UPDATED, db);
    }

    /**
     * 通过id删除Member
     *
     * @param id 编号
     */
    public void delete(String id) {
        Member member = memberDAO.get(id);
        Assert.notNull(member);
        memberDAO.delete(member);
        MessageManager.notify(Member.class, MessageEvent.DELETED, member);
    }

    /**
     * 通过多个id删除Member
     *
     * @param ids id数组
     */
    @Transactional(rollbackFor = Exception.class)
    public void delete(String[] ids) {
        for (String id : ids) {
            delete(id);
        }
    }

    /**
     * 分页查询Member列表
     *
     * @param page     页码
     * @param pagesize 每页条数
     * @return 分页列表
     */
    public PagedList<Member> list(int page, int pagesize) {
        return memberDAO.list(page, pagesize);
    }

    public MemberDAO getMemberDAO() {
        return memberDAO;
    }

    public void setMemberDAO(MemberDAO memberDAO) {
        this.memberDAO = memberDAO;
    }

    /**
     * @param username
     * @return
     * @see works.tonny.apps.user.service.MemberEntityService#getByUsername(java.lang.String)
     */
    public Member getByUsername(String username) {
        return memberDAO.getByUsername(username);
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * works.tonny.core.service.MemberEntityService#updatePassword(java.lang
     * .String, java.lang.String, java.lang.String)
     */
    public void updatePassword(String id, String password, String newPassword) {
        Member member = get(id);
        if (member.getPassword().equals(encryptable.encrypt(password))) {
            member.setPassword(encryptable.encrypt(newPassword));
            memberDAO.update(member);
        } else {
            throw new DataException("用户原密码错误");
        }
    }

    /**
     * @see works.tonny.apps.user.MemberService#lookup(java.lang.String, int,
     * int)
     */
    public PagedList<Member> lookup(String name, int page, int pagesize) {
        return memberDAO.find(name, page, pagesize);
    }

    /**
     * @see works.tonny.apps.user.service.MemberEntityService#findByEmail(java.lang.String)
     */
    public Member findByEmail(String email) {
        return memberDAO.findByEmail(email);
    }

    /**
     * {@inheritDoc}
     *
     * @see works.tonny.apps.user.MemberService#query()
     */
    public MemberQuery query() {
        try {
            return new MemberQueryImpl((BaseDAOSupport) PropertyUtils.getProperty(memberDAO, "targetSource.target"));
        } catch (Exception e) {
            log.error(e);
            return null;
        }
    }

    public class MemberQueryImpl extends AbstractCriteriaQuery<MemberQuery, Member> implements MemberQuery {
        private String name;

        private String username;

        private String email;

        private String userId;

        private String gender;

        private String idNo;

        private String info;

        private String mobile;

        private String no;

        /**
         * @param dao
         */
        public MemberQueryImpl(BaseDAOSupport<Member> dao) {
            this.dao = dao;
        }

        /**
         *
         */
        protected void doBuild() {
            addParameter(name, "name", ListSupport.MUST, ListSupport.LIKE);
            addParameter(username, "username", ListSupport.MUST, ListSupport.EQUALS);
            addParameter(email, "email", ListSupport.MUST, ListSupport.LIKE);
            addParameter(userId, "id", ListSupport.MUST, ListSupport.EQUALS);
            addParameter(gender, "sex", ListSupport.MUST, ListSupport.EQUALS);
            addParameter(idNo, "idNo", ListSupport.MUST, ListSupport.EQUALS);
            addParameter(mobile, "mobileNo", ListSupport.MUST, ListSupport.EQUALS);
            addParameter(info, "info", ListSupport.MUST, ListSupport.LIKE);
            addParameter(no, "no", ListSupport.MUST, ListSupport.EQUALS);
        }

        /**
         * {@inheritDoc}
         *
         * @see works.tonny.apps.user.MemberQuery#name(java.lang.String)
         */
        public MemberQuery name(String name) {
            this.name = name;
            return this;
        }

        /**
         * {@inheritDoc}
         *
         * @see works.tonny.apps.user.MemberQuery#username(java.lang.String)
         */
        public MemberQuery username(String username) {
            this.username = username;
            return this;
        }

        /**
         * {@inheritDoc}
         *
         * @see works.tonny.apps.user.MemberQuery#email(java.lang.String)
         */
        public MemberQuery email(String email) {
            this.email = email;
            return this;
        }

        /**
         * {@inheritDoc}
         *
         * @see works.tonny.apps.user.MemberQuery#userId(java.lang.String)
         */
        public MemberQuery userId(String userId) {
            this.userId = userId;
            return this;
        }

        /**
         * @see works.tonny.apps.user.MemberQuery#gender(java.lang.String)
         */
        @Override
        public MemberQuery gender(String gender) {
            this.gender = gender;
            return this;
        }

        /**
         * @see works.tonny.apps.user.MemberQuery#idNo(java.lang.String)
         */
        @Override
        public MemberQuery idNo(String idNo) {
            this.idNo = idNo;
            return this;
        }

        @Override
        public MemberQuery no(String no) {
            this.no = no;
            return this;
        }

        /**
         * @see works.tonny.apps.user.MemberQuery#info(java.lang.String)
         */
        @Override
        public MemberQuery info(String info) {
            this.info = info;
            return this;
        }

        @Override
        public MemberQuery mobile(String mobile) {
            this.mobile = mobile;
            return this;
        }

        /**
         * {@inheritDoc}
         *
         * @see works.tonny.apps.user.MemberQuery#orderByUsername(java.lang.String)
         */
        public MemberQuery orderByUsername(Direction direction) {
            return orderBy("username", direction);
        }

        /**
         * {@inheritDoc}
         *
         * @see works.tonny.apps.user.MemberQuery#orderByName(java.lang.String)
         */
        public MemberQuery orderByName(Direction direction) {
            return orderBy("name", direction);
        }

        /**
         * {@inheritDoc}
         *
         * @see works.tonny.apps.user.MemberQuery#orderByEmail(java.lang.String)
         */
        public MemberQuery orderByEmail(Direction direction) {
            return orderBy("email", direction);
        }
    }

    /**
     * @return the encryptable
     */
    public Encryptable getEncryptable() {
        return encryptable;
    }

    /**
     * @param encryptable the encryptable to set
     */
    public void setEncryptable(Encryptable encryptable) {
        this.encryptable = encryptable;
    }

}
