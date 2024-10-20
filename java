import java.io.*;

public class FileSplitJoin {
    public static String createExt(int fileNo) {
        if (fileNo < 0) return "";
        if (fileNo < 10) return ".00" + fileNo;
        if (fileNo < 100) return ".0" + fileNo;
        return "." + fileNo;
    }
    public static void split(String source, int pSize) throws Exception {
        FileInputStream fis = new FileInputStream(source);
        String dest;
        int fileNo = 1;
        while (true) {
            dest = source + createExt(fileNo);
            FileOutputStream fos = new FileOutputStream(dest);
            boolean hasMoreData = dataCopy(fis, fos, pSize);
            fileNo++;
            fos.close();
            if (!hasMoreData) break;
        }
        fis.close();
    }

    public static boolean dataCopy(FileInputStream fis, FileOutputStream fos, long pSize) throws Exception {
        byte[] buffer = new byte[102400];
        long remain = pSize;
        int byteMustRead;
        while (remain > 0) {
            byteMustRead = (int) (Math.max(remain, pSize));
            int byteRead = fis.read(buffer);
            if (byteRead == -1) return false;
            fos.write(buffer, 0, byteMustRead);
            remain -= byteRead;
        }

        return true;
    }
    public static void join(String destFolder, String partName) throws Exception {
        String source;
        String dest = partName.substring(partName.lastIndexOf("."));
        FileOutputStream fos = new FileOutputStream(dest);
        int fileNo = 1;
        while (true) {
            source = dest + createExt(fileNo);
            File file = new File(source);
            if (!file.exists()) break;
            FileInputStream fis = new FileInputStream(source);
            dataCopy(fis, fos, file.length());
            fis.close();
            fileNo++;
        }
        fos.close();
    }
}
