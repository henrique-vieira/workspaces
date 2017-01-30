package br.com.sistemaxm.entidades;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.List;

public class Md5Criptografy {


		// Função para criar hash da senha informada
		public static String md5(String senha) throws Exception {
			String sen = "";
			MessageDigest md = null;

			md = MessageDigest.getInstance("MD5");

			BigInteger hash = new BigInteger(1, md.digest(senha.getBytes()));
			sen = hash.toString(16);

			return sen;
		}

	}

