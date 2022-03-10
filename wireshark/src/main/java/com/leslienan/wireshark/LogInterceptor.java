package com.leslienan.wireshark;

import java.io.IOException;

import android.os.Build;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;

/**
 * Author by haonan, Date on 3/24/21.
 * Email:278913810@qq.com
 * PS:
 */
public class LogInterceptor implements Interceptor {
    private static final String TAG = "API";

    @NonNull
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request oldRequest = chain.request();
        Request.Builder newRequestBuild;
        String method = oldRequest.method();
        String postBodyString = "";//post参数
        if ("GET".equals(method)) {
            // 添加新的参数
            HttpUrl.Builder commonParamsUrlBuilder = oldRequest.url()
                    .newBuilder()
                    .scheme(oldRequest.url().scheme())
                    .host(oldRequest.url().host());
            newRequestBuild = oldRequest.newBuilder()
                    .method(oldRequest.method(), oldRequest.body())
                    .url(commonParamsUrlBuilder.build());
        } else {
            newRequestBuild = oldRequest.newBuilder();
            postBodyString = bodyToString(oldRequest.body());
        }
        Request newRequest= newRequestBuild
//                .addHeader("authorization", mToken)//TextUtils.isEmpty(mToken) ? "Basic TWlsZWFnZUJhbzpjYXJsaW54ODg4" :
                .addHeader("Accept-Encoding", "identity")
                .build();

        long startTime = System.currentTimeMillis();
        Response response = chain.proceed(newRequest);
        long endTime = System.currentTimeMillis();
        long duration = endTime - startTime;
        MediaType mediaType = response.body().contentType();
        String content = response.body().string();
        int httpStatus = response.code();
        String responseData = content;
        StringBuilder logSB = new StringBuilder();
        String request = newRequest.toString();
        logSB.append("httpCode:").append(httpStatus).append(",");
        logSB.append(duration).append("毫秒\n");
        logSB.append("Header: ").append(newRequest.headers().toString()).append("\n");
        if (method.equalsIgnoreCase("POST") || method.equalsIgnoreCase("PUT")) {
            logSB.append("post参数:\n");
            if ((postBodyString.startsWith("{") && postBodyString.endsWith("}"))) {
                postBodyString = formatJson(decodeUnicode(postBodyString));
            }
            logSB.append(postBodyString).append("\n");
        }
        request = request.replace("Request", "");
        if ((request.startsWith("{") && request.endsWith("}"))) {
            request = formatJson(decodeUnicode(request));
        }
        logSB.append("Request:\n").append(request).append("\n");
        // 以{}或者[]形式的说明是响应结果的json数据，需要进行格式化
        if ((content.startsWith("{") && content.endsWith("}"))
                || (content.startsWith("[") && content.endsWith("]"))) {
            content = formatJson(decodeUnicode(content));
        }
        logSB.append("Response:\n");
        logSB.append(content.concat("\n"));
        logSB.append("返回数据:").append(responseData);
        //Logger.t(TAG).i(logSB.toString());
        return response.newBuilder()
                .body(ResponseBody.create(mediaType, responseData))
                .build();
    }

    private String bodyToString(final RequestBody request) {
        try {
            final Buffer buffer = new Buffer();
            if (request != null)
                request.writeTo(buffer);
            else
                return "";
            return buffer.readUtf8();
        } catch (final IOException e) {
            return "did not work";
        }
    }

    /**
     * 格式化json字符串
     *
     * @param jsonStr 需要格式化的json串
     * @return 格式化后的json串
     */
    public static String formatJson(String jsonStr) {
        if (null == jsonStr || "".equals(jsonStr)) return "";
        StringBuilder sb = new StringBuilder();
        char last;
        char current = '\0';
        int indent = 0;
        for (int i = 0; i < jsonStr.length(); i++) {
            last = current;
            current = jsonStr.charAt(i);
            //遇到{ [换行，且下一行缩进
            switch (current) {
                case '{':
                case '[':
                    sb.append(current);
                    sb.append('\n');
                    indent++;
                    addIndentBlank(sb, indent);
                    break;
                //遇到} ]换行，当前行缩进
                case '}':
                case ']':
                    sb.append('\n');
                    indent--;
                    addIndentBlank(sb, indent);
                    sb.append(current);
                    break;
                //遇到,换行
                case ',':
                    sb.append(current);
                    if (last != '\\') {
                        sb.append('\n');
                        addIndentBlank(sb, indent);
                    }
                    break;
                default:
                    sb.append(current);
            }
        }
        return sb.toString();
    }

    /**
     * 添加space
     */
    private static void addIndentBlank(StringBuilder sb, int indent) {
        for (int i = 0; i < indent; i++) {
            sb.append('\t');
        }
    }


    /**
     * http 请求数据返回 json 中中文字符为 unicode 编码转汉字转码
     *
     * @param theString 转换字符串
     * @return 转化后的结果.
     */
    public static String decodeUnicode(String theString) {
        char aChar;
        int len = theString.length();
        StringBuffer outBuffer = new StringBuffer(len);
        for (int x = 0; x < len; ) {
            aChar = theString.charAt(x++);
            if (aChar == '\\') {
                aChar = theString.charAt(x++);
                if (aChar == 'u') {
                    int value = 0;
                    for (int i = 0; i < 4; i++) {
                        aChar = theString.charAt(x++);
                        switch (aChar) {
                            case '0':
                            case '1':
                            case '2':
                            case '3':
                            case '4':
                            case '5':
                            case '6':
                            case '7':
                            case '8':
                            case '9':
                                value = (value << 4) + aChar - '0';
                                break;
                            case 'a':
                            case 'b':
                            case 'c':
                            case 'd':
                            case 'e':
                            case 'f':
                                value = (value << 4) + 10 + aChar - 'a';
                                break;
                            case 'A':
                            case 'B':
                            case 'C':
                            case 'D':
                            case 'E':
                            case 'F':
                                value = (value << 4) + 10 + aChar - 'A';
                                break;
                            default:
                                throw new IllegalArgumentException(
                                        "Malformed   \\uxxxx   encoding.");
                        }

                    }
                    outBuffer.append((char) value);
                } else {
                    if (aChar == 't')
                        aChar = '\t';
                    else if (aChar == 'r')
                        aChar = '\r';
                    else if (aChar == 'n')
                        aChar = '\n';
                    else if (aChar == 'f')
                        aChar = '\f';
                    outBuffer.append(aChar);
                }
            } else
                outBuffer.append(aChar);
        }
        return outBuffer.toString();
    }
}

