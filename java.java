import java.io.*;

public class FileSplitJoin {
    
	public static void main(String[] args) throws Exception {

		String path = "E:\\BuildedPJ\\StickerArtBook.apk";

		split(path, 90097507);
		
//		FileOutputStream fos = new FileOutputStream("E:\\BuildedPJ\\test.1");
//		fos.close();
	}

	public static String createExt(int fileNo) {

		if (fileNo < 0)
			return "";

		if (fileNo < 10)
			return ".00" + fileNo;

		if (fileNo < 100)
			return ".0" + fileNo;

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

			if (!hasMoreData)
				break;
		}

		fis.close();
	}

	public static boolean dataCopy(FileInputStream fis, FileOutputStream fos, int pSize) throws Exception {
		byte[] buffer = new byte[102400];
		int remain = pSize;
		int readByte;
		int reqByte;

		while (remain > 0) {

			reqByte = (remain < buffer.length) ? remain : buffer.length;
			
			readByte = fis.read(buffer, 0, reqByte);

			if (readByte == -1)
				return false;

			fos.write(buffer, 0, readByte);

			remain -= readByte;
		}

		return true;
	}
}
