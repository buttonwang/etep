package com.ambow.core.security.jcaptcha;

import java.awt.Color;
import java.awt.Font;

import com.octo.captcha.component.image.backgroundgenerator.BackgroundGenerator;
import com.octo.captcha.component.image.backgroundgenerator.UniColorBackgroundGenerator;
import com.octo.captcha.component.image.fontgenerator.FontGenerator;
import com.octo.captcha.component.image.fontgenerator.RandomFontGenerator;
import com.octo.captcha.component.image.textpaster.RandomTextPaster;
import com.octo.captcha.component.image.textpaster.TextPaster;
import com.octo.captcha.component.image.wordtoimage.ComposedWordToImage;
import com.octo.captcha.component.image.wordtoimage.WordToImage;
import com.octo.captcha.component.word.wordgenerator.RandomWordGenerator;
import com.octo.captcha.component.word.wordgenerator.WordGenerator;
import com.octo.captcha.engine.image.ListImageCaptchaEngine;
import com.octo.captcha.image.gimpy.GimpyFactory;

public class ImageCaptchaEngineImpl extends ListImageCaptchaEngine {
	protected void buildInitialFactories() {
		WordGenerator wordGenerator = (new RandomWordGenerator(
				"0123456789abcdefghijkmnpqrstuvwxyz"));

		// Integer minAcceptedWordLength, Integer maxAcceptedWordLength,Color[]
		// textColors
		TextPaster textPaster = new RandomTextPaster(4, 4, Color.blue);

		// ��ɫ�����������Լ�ʵ�� Integer width, Integer height
		// BackgroundGenerator backgroundGenerator = new
		// FunkyBackgroundGenerator(
		// new Integer(100), new Integer(20));

		BackgroundGenerator backgroundGenerator = new UniColorBackgroundGenerator(
				86, 28, Color.WHITE);

		// Integer minFontSize, Integer maxFontSize
		FontGenerator fontGenerator = new RandomFontGenerator(15, 15,
				new Font[] { new Font("System", 0, 15) });

		WordToImage wordToImage = new ComposedWordToImage(fontGenerator,
				backgroundGenerator, textPaster);

		addFactory(new GimpyFactory(wordGenerator, wordToImage));
	}
}
