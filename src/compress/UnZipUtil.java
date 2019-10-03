package compress;

import java.io.*;
import java.nio.charset.Charset;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/**
 * @Description 解压缩工具
 * @Author pc
 * @Date 2019/10/03 18:21
 */
public class UnZipUtil {

    /**
     * @param zipfile   压缩文件
     * @param targetDir 解压后的目标路径
     * @Description 解压缩文件
     * @Author guojianfeng
     * @Date 2019/10/03 18:23
     * @Return
     */
    public static boolean decompressing(File zipfile, String targetDir) {
        try {

            Charset gbk = Charset.forName("gbk");
            ZipInputStream zin = new ZipInputStream(new FileInputStream(zipfile), gbk);
            BufferedInputStream bin = new BufferedInputStream(zin);
            ZipEntry entry;
            File unzipFile = null;
            while ((entry = zin.getNextEntry()) != null && !entry.isDirectory()){
                unzipFile = new File(targetDir,entry.getName());
                //如果解压缩文件不存在，就新建路径
                if (!unzipFile.exists()){
                    new File(unzipFile.getParent()).mkdirs();
                }
                FileOutputStream out = new FileOutputStream(unzipFile);
                BufferedOutputStream bout = new BufferedOutputStream(out);
                int b;
                while ((b = bin.read()) != -1){
                    bout.write(b);
                }
                bout.close();
                out.close();
            }
            bin.close();
            zin.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        }catch (IOException e){
            e.printStackTrace();
            return false;
        }
        return true;
    }

}
