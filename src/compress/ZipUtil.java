package compress;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * @Description 压缩工具
 * @Author pc
 * @Date 2019/10/03 21:15
 */
public class ZipUtil {
    private static final int BUFFER_SIZE = 2 * 1024;

    /**
     * @param srcDir           压缩文件夹路径
     * @param out              压缩文件输出流
     * @param keepDirStructure 是否保留原来的目录结构，true：保留目录结构；false：所有文件放至压缩包根目录下
     *                         （注意：不保留目录结构可能会出现同名文件，导致压缩失败）
     * @Description 压缩方法1
     * @Author guojianfeng
     * @Date 2019/10/03 21:33
     * @Return
     */
    public static void toZip(String srcDir, OutputStream out, boolean keepDirStructure) {
        long start = System.currentTimeMillis();
        ZipOutputStream zos = null;
        try {
            zos = new ZipOutputStream(out);
            File sourceFile = new File(srcDir);
            compressing(sourceFile, zos, sourceFile.getName(), keepDirStructure);
            long end = System.currentTimeMillis();
            System.out.println("压缩完成，耗时：" + (end - start) + "ms");

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (zos != null) {
                try {
                    zos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    /**
     * @param srcFiles     需要压缩的文件列表
     * @param outputStream 压缩文件输出流
     * @Description 压缩方法2
     * @Author guojianfeng
     * @Date 2019/10/03 21:44
     * @Return
     */
    public static void toZip(List<File> srcFiles, OutputStream outputStream) {
        long start = System.currentTimeMillis();
        ZipOutputStream zos = null;
        try {
            zos = new ZipOutputStream(outputStream);
            for (File srcFile : srcFiles) {
                byte[] buf = new byte[BUFFER_SIZE];
                zos.putNextEntry(new ZipEntry(srcFile.getName()));
                int len;
                FileInputStream in = new FileInputStream(srcFile);
                while ((len = in.read(buf)) != -1) {
                    zos.write(buf, 0, len);
                }
                zos.closeEntry();
                in.close();
            }
            long end = System.currentTimeMillis();
            System.out.println("压缩完成，耗时：" + (end - start) + "ms");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (zos != null) {
                try {
                    zos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    /**
     * @param sourceFile       源文件
     * @param keepDirStructure 是否保留原来的目录结构，true：保留目录结构；false：所有文件放至压缩包根目录下
     *                         （注意：不保留目录结构可能会出现同名文件，导致压缩失败）
     * @Description 递归压缩方法
     * @Author guojianfeng
     * @Date 2019/10/03 21:20
     * @Return
     */
    private static void compressing(File sourceFile, ZipOutputStream zos, String name, boolean keepDirStructure) throws Exception {

        byte[] buf = new byte[BUFFER_SIZE];
        if (sourceFile.isFile()) {
            //向zip输出流中添加一个zip实体，构造器中name为zip实体的文件的名字
            zos.putNextEntry(new ZipEntry(name));
            //copy文件到zip输出流中
            int len;
            FileInputStream in = new FileInputStream(sourceFile);
            while ((len = in.read(buf)) != -1) {
                zos.write(buf, 0, len);
            }
            zos.closeEntry();
            in.close();
        } else {
            File[] listFiles = sourceFile.listFiles();
            if (listFiles == null || listFiles.length == 0) {
                //需要保留原來的文件结构时，需要对空文件夹进行处理
                if (keepDirStructure) {
                    //空文件夹的处理
                    zos.putNextEntry(new ZipEntry(name + "/"));
                    //没有文件，不需要文件的copy
                    zos.closeEntry();
                }
            } else {
                for (File file : listFiles) {
                    //判断是否需要保留原来的文件结构
                    if (keepDirStructure) {
                        // 注意：file.getName()前面需要带上父文件夹的名字加一斜杠,
                        // 不然最后压缩包中就不能保留原来的文件结构,即：所有文件都跑到压缩包根目录下了
                        compressing(file, zos, name + "/" + file.getName(), keepDirStructure);
                    } else {
                        compressing(file, zos, file.getName(), keepDirStructure);
                    }
                }
            }
        }
    }
}






