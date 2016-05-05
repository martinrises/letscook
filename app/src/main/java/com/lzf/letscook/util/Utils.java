package com.lzf.letscook.util;

import android.text.TextUtils;

import com.lzf.letscook.net.UrlContainer;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Map;

/**
 * Created by liuzhaofeng on 16/4/30.
 */
public class Utils {

    public static String md5Encode(String s) {
        if (TextUtils.isEmpty(s)) {
            return "";
        }

        return Md5Encode.encode(s);
    }

    public static void signParam(String url, Map<String, String> params) {
        if (TextUtils.isEmpty(url) || params == null) {
            return;
        }

        params.put("sign_ran", md5Encode(String.valueOf(UrlContainer.mRandom.nextInt())));

        ArrayList<Map.Entry<String, String>> list = new ArrayList<>(params.entrySet());
        Collections.sort(list, new Comparator<Map.Entry<String, String>>() {
            @Override
            public int compare(final Map.Entry<String, String> kv, final Map.Entry<String, String> kv1) {
                return kv.getKey().compareTo(kv1.getKey());
            }
        });

        final StringBuilder sb = new StringBuilder();
        for (int i = 0, size = list.size(); i < size; ++i) {
            final Map.Entry<String, String> nameValuePair = list.get(i);
            sb.append(String.valueOf(nameValuePair.getKey()) + "=" + nameValuePair.getValue());
            if (i < size - 1) {
                sb.append("&");
            }
        }
        sb.append("&url=" + md5Encode(url));
        sb.append("&p=Nha1Alsdp8");
        params.put("code", createSign(md5Encode(sb.toString())));
    }

    private static String createSign(final String ex) {
        final StringBuffer sb = new StringBuffer();
        MessageDigest instance = null;
        while (true) {
            try {
                instance = MessageDigest.getInstance("MD5");
                instance.update(((String) ex).getBytes("utf-8"));
                final byte[] digest = instance.digest();
                for (int i = 0; i < digest.length; ++i) {
                    final int n = digest[i] & 0xFF;
                    final int n2 = n >>> 4;
                    final int n3 = n & 0xF;
                    int n4;
                    if (n2 < 10) {
                        n4 = 48;
                    } else {
                        n4 = 87;
                    }
                    sb.append((char) (n4 + n2));
                    int n5;
                    if (n3 < 10) {
                        n5 = 48;
                    } else {
                        n5 = 87;
                    }
                    sb.append((char) (n5 + n3));
                }
                return sb.toString().substring(5, 21);
            } catch (NoSuchAlgorithmException ex2) {
                continue;
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            break;
        }
        return "";
    }

    public static int parseInt(String s){
        if(TextUtils.isEmpty(s)){
            return 0;
        }

        try{
            return Integer.valueOf(s);
        }catch (Exception e){
            return 0;
        }
    }

    public static boolean isCollectionEmpty(Collection c){
        return c == null || c.isEmpty();
    }
}
