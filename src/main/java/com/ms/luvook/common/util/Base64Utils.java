package com.ms.luvook.common.util;

import java.util.Base64;

public class Base64Utils {
	private static final String BASE_64_PREFIX = "data:image/jpeg;base64,";

	public static byte[] decodeBase64ToBytes(String encodeImg) {
		if (encodeImg.startsWith(BASE_64_PREFIX))
			return Base64.getDecoder().decode(encodeImg.substring(BASE_64_PREFIX.length()));
		else
			throw new IllegalStateException("it is not base 64 string");
	}

}