package com.coralline.sea;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;
import java.util.Properties;

/* JADX INFO: loaded from: assets/RiskStub.dex */
public class s4 {
    public static String a(File file, String str) throws Throwable {
        FileInputStream fileInputStream;
        try {
            try {
                fileInputStream = new FileInputStream(file);
            } catch (Throwable th) {
                th = th;
                fileInputStream = null;
            }
        } catch (IOException e) {
            e = e;
        }
        try {
            StringWriter stringWriter = new StringWriter();
            PrintWriter printWriter = new PrintWriter(stringWriter);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(fileInputStream, str));
            while (true) {
                String line = bufferedReader.readLine();
                if (line == null) {
                    String string = stringWriter.toString();
                    try {
                        fileInputStream.close();
                        return string;
                    } catch (Exception e2) {
                        return string;
                    }
                }
                printWriter.println(line);
            }
        } catch (IOException e3) {
            e = e3;
            throw new RuntimeException(e);
        } catch (Throwable th2) {
            th = th2;
            if (fileInputStream != null) {
                try {
                    fileInputStream.close();
                } catch (Exception e4) {
                }
            }
            throw th;
        }
    }

    public static String a(InputStream inputStream, String str) {
        try {
            try {
                String strC = t4.c(inputStream, str);
                try {
                    return strC;
                } catch (Exception e) {
                    return strC;
                }
            } catch (Exception e2) {
                throw new RuntimeException(e2);
            }
        } finally {
            try {
                inputStream.close();
            } catch (Exception e3) {
            }
        }
    }

    public static void a(File file, File file2) {
        if (!file.isDirectory()) {
            try {
                b(new FileInputStream(file), new FileOutputStream(file2));
                return;
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        if (!file2.exists()) {
            file2.mkdir();
        }
        for (String str : file.list()) {
            a(new File(file, str), new File(file2, str));
        }
    }

    public static void a(InputStream inputStream, File file) throws Throwable {
        FileOutputStream fileOutputStream;
        FileOutputStream fileOutputStream2 = null;
        try {
            try {
                fileOutputStream = new FileOutputStream(file);
            } catch (IOException e) {
                e = e;
            }
        } catch (Throwable th) {
            th = th;
            fileOutputStream = fileOutputStream2;
        }
        try {
            byte[] bArr = new byte[8096];
            while (true) {
                int i = inputStream.read(bArr);
                if (i > 0) {
                    fileOutputStream.write(bArr, 0, i);
                } else {
                    try {
                        break;
                    } catch (Exception e2) {
                    }
                }
            }
            inputStream.close();
            try {
                fileOutputStream.close();
            } catch (Exception e3) {
            }
        } catch (IOException e4) {
            e = e4;
            fileOutputStream2 = fileOutputStream;
            throw new RuntimeException(e);
        } catch (Throwable th2) {
            th = th2;
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (Exception e5) {
                }
            }
            if (fileOutputStream == null) {
                throw th;
            }
            try {
                fileOutputStream.close();
                throw th;
            } catch (Exception e6) {
                throw th;
            }
        }
    }

    public static void a(InputStream inputStream, OutputStream outputStream) {
        try {
            try {
                byte[] bArr = new byte[8096];
                while (true) {
                    int i = inputStream.read(bArr);
                    if (i <= 0) {
                        try {
                            inputStream.close();
                            return;
                        } catch (Exception e) {
                            return;
                        }
                    }
                    outputStream.write(bArr, 0, i);
                }
            } catch (IOException e2) {
                throw new RuntimeException(e2);
            }
        } catch (Throwable th) {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (Exception e3) {
                }
            }
            throw th;
        }
    }

    public static void a(CharSequence charSequence, File file) throws Throwable {
        a(charSequence, file, "utf-8");
    }

    public static void a(CharSequence charSequence, File file, String str) throws Throwable {
        FileOutputStream fileOutputStream;
        FileOutputStream fileOutputStream2 = null;
        try {
            try {
                fileOutputStream = new FileOutputStream(file);
            } catch (IOException e) {
                e = e;
            }
        } catch (Throwable th) {
            th = th;
            fileOutputStream = fileOutputStream2;
        }
        try {
            PrintWriter printWriter = new PrintWriter(new OutputStreamWriter(fileOutputStream, str));
            printWriter.println(charSequence);
            printWriter.flush();
            fileOutputStream.flush();
            try {
                fileOutputStream.close();
            } catch (Exception e2) {
            }
        } catch (IOException e3) {
            e = e3;
            fileOutputStream2 = fileOutputStream;
            throw new RuntimeException(e);
        } catch (Throwable th2) {
            th = th2;
            if (fileOutputStream != null) {
                try {
                    fileOutputStream.close();
                } catch (Exception e4) {
                }
            }
            throw th;
        }
    }

    public static void a(CharSequence charSequence, OutputStream outputStream) {
        a(charSequence, outputStream, "utf-8");
    }

    public static void a(CharSequence charSequence, OutputStream outputStream, String str) {
        try {
            try {
                PrintWriter printWriter = new PrintWriter(new OutputStreamWriter(outputStream, str));
                printWriter.println(charSequence);
                printWriter.flush();
                outputStream.flush();
                try {
                    outputStream.close();
                } catch (Exception e) {
                }
            } catch (IOException e2) {
                throw new RuntimeException(e2);
            }
        } catch (Throwable th) {
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (Exception e3) {
                }
            }
            throw th;
        }
    }

    public static void a(byte[] bArr, File file) throws Throwable {
        FileOutputStream fileOutputStream;
        FileOutputStream fileOutputStream2 = null;
        try {
            try {
                fileOutputStream = new FileOutputStream(file);
            } catch (IOException e) {
                e = e;
            }
        } catch (Throwable th) {
            th = th;
            fileOutputStream = fileOutputStream2;
        }
        try {
            fileOutputStream.write(bArr);
            fileOutputStream.flush();
            try {
                fileOutputStream.close();
            } catch (Exception e2) {
            }
        } catch (IOException e3) {
            e = e3;
            fileOutputStream2 = fileOutputStream;
            throw new RuntimeException(e);
        } catch (Throwable th2) {
            th = th2;
            if (fileOutputStream != null) {
                try {
                    fileOutputStream.close();
                } catch (Exception e4) {
                }
            }
            throw th;
        }
    }

    public static byte[] a(File file) throws Throwable {
        FileInputStream fileInputStream;
        try {
            try {
                fileInputStream = new FileInputStream(file);
            } catch (Throwable th) {
                th = th;
                fileInputStream = null;
            }
        } catch (IOException e) {
            e = e;
        }
        try {
            byte[] bArr = new byte[(int) file.length()];
            fileInputStream.read(bArr);
            try {
                fileInputStream.close();
                return bArr;
            } catch (Exception e2) {
                return bArr;
            }
        } catch (IOException e3) {
            e = e3;
            throw new RuntimeException(e);
        } catch (Throwable th2) {
            th = th2;
            if (fileInputStream != null) {
                try {
                    fileInputStream.close();
                } catch (Exception e4) {
                }
            }
            throw th;
        }
    }

    public static byte[] a(InputStream inputStream) {
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            byte[] bArr = new byte[8096];
            while (true) {
                int i = inputStream.read(bArr);
                if (i <= 0) {
                    return byteArrayOutputStream.toByteArray();
                }
                byteArrayOutputStream.write(bArr, 0, i);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static String b(File file) {
        return a(file, "utf-8");
    }

    public static String b(InputStream inputStream) {
        return a(inputStream, "utf-8");
    }

    public static List<String> b(File file, String str) throws Throwable {
        FileInputStream fileInputStream;
        FileInputStream fileInputStream2 = null;
        try {
            try {
                fileInputStream = new FileInputStream(file);
            } catch (IOException e) {
                e = e;
            }
        } catch (Throwable th) {
            th = th;
            fileInputStream = fileInputStream2;
        }
        try {
            List<String> listA = t4.a(fileInputStream, str);
            try {
                fileInputStream.close();
                return listA;
            } catch (Exception e2) {
                return listA;
            }
        } catch (IOException e3) {
            e = e3;
            fileInputStream2 = fileInputStream;
            throw new RuntimeException(e);
        } catch (Throwable th2) {
            th = th2;
            if (fileInputStream != null) {
                try {
                    fileInputStream.close();
                } catch (Exception e4) {
                }
            }
            throw th;
        }
    }

    public static void b(InputStream inputStream, OutputStream outputStream) {
        try {
            try {
                byte[] bArr = new byte[8096];
                while (true) {
                    int i = inputStream.read(bArr);
                    if (i > 0) {
                        outputStream.write(bArr, 0, i);
                    } else {
                        try {
                            break;
                        } catch (Exception e) {
                        }
                    }
                }
                inputStream.close();
                if (outputStream != null) {
                    try {
                        outputStream.close();
                    } catch (Exception e2) {
                    }
                }
            } finally {
            }
        } catch (IOException e3) {
            throw new RuntimeException(e3);
        }
    }

    public static List<String> c(File file) {
        return b(file, "utf-8");
    }

    public static List<String> c(InputStream inputStream) {
        try {
            return t4.c(inputStream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static Properties d(InputStream inputStream) {
        Properties properties = new Properties();
        try {
            properties.load(inputStream);
            inputStream.close();
            return properties;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
