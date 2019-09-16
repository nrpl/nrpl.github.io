# How to use Preview Features in Java
Preview Features are disabled by default.
To try them out, you have to enable them at compile time.
For example in Java 13 you can use Text Blocks:
```
public class Testbed {
	public static void main(String... args) {
		System.out.println("""
		Hello World!
		This is a Text Block in Java 13.
		See you later.""");
	}
}
```

If you try to compile this class with Java 13 (`javac Testbed.java`), the compiler shows you an error message:
```
Testbed.java:3: error: text blocks are a preview feature and are disabled by default.
                System.out.println("""
                                   ^
  (use --enable-preview to enable text blocks)
1 error
```

To fix this error, you have to add the enable-preview flag: `javac --enable-preview --release 13 Testbed.java`
To run a class containing preview features, you have to enable the preview flag aswell: `java --enable-previewenable-preview Testbed`
