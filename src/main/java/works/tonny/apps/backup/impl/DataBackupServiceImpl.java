package works.tonny.apps.backup.impl;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.llama.library.utils.DateUtils;
import org.llama.library.utils.ELHelper;
import org.llama.library.utils.PagedList;
import org.llama.library.utils.WebAppPath;
import org.springframework.transaction.annotation.Transactional;
import works.tonny.apps.backup.Backup;
import works.tonny.apps.backup.DataBackupService;
import works.tonny.apps.support.HibernateDAO;
import works.tonny.apps.user.AuthedAbstractService;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

/**
 * Created by tonny on 2015/11/28.
 */
public class DataBackupServiceImpl extends AuthedAbstractService implements DataBackupService {
    private HibernateDAO daoSupport;

    private String dir;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void backup(Class... clazzs) {
        StringBuffer buffer = new StringBuffer();
        Map<Class, Long> map = new HashMap<Class, Long>();
        //        map.put("backup_time", new Date());
        for (Class clazz : clazzs) {
            buffer.append(clazz.getSimpleName()).append("_");
            //            map.put(clazz.getName(), new Long(0));
        }
        buffer.append(DateUtils.toString(new Date(), "yyyyMMddHHmmss"));
        Map<String, Object> context = getPathContext();
        String execute = ELHelper.execute(dir, context);
        File temp = new File(execute, buffer.toString() + ".tmp");
        if (!temp.getParentFile().exists()) {
            temp.getParentFile().mkdirs();
        }


        FileOutputStream fos = null;
        ObjectOutputStream oos = null;

        try {
            fos = new FileOutputStream(temp);
            oos = new ObjectOutputStream(fos);
            for (Class clazz : clazzs) {
                int offset = 0;
                long count = 0;
                while (true) {
                    PagedList list = daoSupport.list("from " + clazz.getName(), offset, 200);
                    count += list.size();
                    if (list.isEmpty()) {
                        break;
                    }
                    for (Object o : list) {
                        oos.writeObject(o);
                    }
                    oos.flush();
                    offset += 200;
                }
                map.put(clazz, count);
            }
            //            oos.flush();
        } catch (IOException e) {
            log.error(e);
        } finally {
            IOUtils.closeQuietly(oos);
            IOUtils.closeQuietly(fos);
        }


        FileInputStream fis = null;
        ObjectInputStream ois = null;

        File target = new File(execute, buffer.toString() + ".backup");

        int count = 0;
        try {
            fos = new FileOutputStream(target);
            oos = new ObjectOutputStream(fos);
            oos.writeObject(new Date());
            oos.writeObject(map);
            fis = new FileInputStream(temp);
            ois = new ObjectInputStream(fis);
            while (true) {
                Object o = ois.readObject();
                oos.writeObject(o);
                if (count++ % 100 == 0) {
                    oos.flush();
                }
            }


        } catch (EOFException e) {

        } catch (IOException e) {
            log.error(e);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            IOUtils.closeQuietly(oos);
            IOUtils.closeQuietly(fos);
            IOUtils.closeQuietly(fis);
            IOUtils.closeQuietly(ois);
            FileUtils.deleteQuietly(temp);
        }
    }

    @Override
    public List<Backup> files() {
        Map<String, Object> context = getPathContext();
        String execute = ELHelper.execute(dir, context);
        File file = new File(execute);
        File[] files = file.listFiles();
        Arrays.sort(files, new Comparator<File>() {
            @Override
            public int compare(File o1, File o2) {
                return new Long(o1.lastModified()).compareTo(o2.lastModified());
            }
        });
        List<Backup> bs = new ArrayList<Backup>();
        try {
            for (File f : files) {
                ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(f));
                //                Map<Object, Object> o = (Map<Object, Object>) objectInputStream.readObject();
                Backup b = new Backup();
                b.setBackupTime((Date) objectInputStream.readObject());
                b.setSizes((Map<Class, Long>) objectInputStream.readObject());
                bs.add(b);
                b.setFile(f);
                objectInputStream.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return bs;
    }

    @Override
    public void recorver(File[] files) {
        for (File file : files) {
            try {
                ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(file));
                //                objectInputStream
                System.out.println(objectInputStream.readObject());
                System.out.println(objectInputStream.readObject());
                while (true) {
                    Object o = objectInputStream.readObject();
                    if (o == null) {
                        break;
                    }
                    System.out.println(BeanUtils.describe(o));

                    Object object = daoSupport.get(o.getClass(), BeanUtils.getProperty(o, "id"));
                    if (object != null) {
                        daoSupport.update(o);
                    } else {
                        daoSupport.save(o);
                    }

                }
            } catch (EOFException e) {
                //                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public File backupDir() {
        Map<String, Object> context = getPathContext();
        String execute = ELHelper.execute(dir, context);
        File file = new File(execute);
        return file;
    }

    protected Map<String, Object> getPathContext() {
        Map<String, Object> context = new HashMap<String, Object>();
        context.put("webroot", WebAppPath.webRootPath());
        return context;
    }

    public HibernateDAO getDaoSupport() {
        return daoSupport;
    }

    public void setDaoSupport(HibernateDAO daoSupport) {
        this.daoSupport = daoSupport;
    }

    public String getDir() {
        return dir;
    }

    public void setDir(String dir) {
        this.dir = dir;
    }
}
