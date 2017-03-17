Automatisierte Modultests am Beispiel des Testframeworks JUnit
==============================================================

Einführung
----------
Bei der Entwicklung von Software ist es praktisch unmöglich eine komplette Anwendung oder auch nur ein Feature direkt und vollständig aus dem Kopf in Code zu gießen.
Schon bei der Implementierung einfacher Problemstellungen tauchen unerwartete Stolpersteine auf,
die zu fehlerhaften Zuständen und Fehlverhalten der Anwendung führen können.

Umso wichtiger ist es daher, die Anwendung schrittweise zu entwickeln und dabei immer wieder Testläufe durchzuführen.
Vor allem wenn die Anwendung komplexer wird, wird es immer schwieriger diese Testläufe manuell durchzuführen.
Es müssen verschiedenste Kombinationen von Eingabedaten bzw. Systemzuständen berücksichtigt werden.

Anpassungen und Erweiterungen von vorhandenem Code können leicht dazu führen, dass ein neues Feature zwar funktioniert,
aber bereits bestehende Funktionalität sich plötzlich nicht mehr wie gewünscht verhält.
Bei Änderungen an vorhandenem Code müssen daher auch immer bereits etablierte Funktionalitäten erneut überprüft werden (Regressionstests).

Das manuelle Überprüfen der Ausgaben bzw. des Verhaltens der Anwendung und Abgleich mit der Spezifikation wird bei steigender Komplexität zur unlösbaren Fleissaufgabe.
Die Lösung: Automatisiertes Testen.

Beim automatisierten Testen mit Frameworks wie z.B. JUnit oder TestNG werden die Testfälle in Form von Java Code erstellt.
Das gewünschte Verhalten der Anwendung ist damit in Form von ausführbarem Code spezifiziert und dokumentiert.

Unittests sind Modultests, bei denen einzelne Einheiten/Module, in der Praxis sind das Klassen, getestet werden.
Die Klassen werden isoliert voneinander getestet, sodass nicht das Zusammenwirken der Bausteine als System getestet wird,
sondern nur die Logik innerhalb einer Klasse.

Das hat den Vorteil, dass die Tests einfach und übersichtlich bleiben und vor allem auch schnell ausgeführt werden können.
Dadurch können Tests problemlos nach jeder Code Änderung wiederholt ausgeführt werden.
Die automatisierte Ausführung der Tests kann als Teil des automatisierten Build Prozesses (z.B. in Jenkins) in den Entwicklungsprozess aufgenommen werden.
Zusätzlich können die Tests jederzeit bei Bedarf manuell in der lokalen Entwicklungsumgebung ausgeführt werden.


Das Prinzip von Unit Tests
--------------------------
Im folgenden wird das Prinzip von Unittests an einem einfachen Beispiel demonstriert.
Dabei wird zunächst ein Codebeispiel erstellt und manuell getestet.
Danach wird die Testlogik im Code abgebildet.
Die Testlogik wird zunächst ohne ein Testframework erstellt, um das Konzept zu demonstrieren.
Anschließend wird die Testlogik mit Hilfe von JUnit umgesetzt.

Als Grundlage dient [TDD Kata 1](http://osherove.com/tdd-kata-1/) von Roy Osherove.
Ausgangspunkt ist die Klasse StringCalculator, deren Methode add einen String mit kommaseparierten Zahlen entgegennimmt und die Summe der Zahlen zurückgibt:

	public class StringCalculator {
		public int add(String numbers) {
			if(numbers.isEmpty()) {
				return 0;
			}
			int sum = 0;
			String[] tokens = numbers.split(",");
			for(String token : tokens) {
				sum += Integer.parseInt(token.trim());
			}
			return sum;
		}
	}

Um diese Klasse manuell zu Testen, kann man folgendermaßen vorgehen:

	public class Testbed {
		public static void main(String... args) {
			StringCalculator stringCalculator = new StringCalculator();
			String numbers = "1,2,3";
			int sum = stringCalculator.add(numbers);
			System.out.println("Summe von [" +numbers +"] ist " +sum);
		}
	}

Die Klasse Testbed verwendet die zu testende Klasse StringCalculator und ruft dessen add Methode auf.
Das Ergebnis wird mit println auf der Konsole ausgegeben.

Kompilieren und Ausführen:
	javac Testbed.java && java Testbed

Ausgabe:
	Summe von [1,2,3] ist 6

Das Ergebnis muss manuell verifiziert werden, indem das erwartete Ergebnis aus Basis des Eingabestrings mit dem tatsächlichen verglichen wird.

Der Vergleich von erwartetem und tatsächlichem Ergebnis kann aber auch direkt im Code implementiert werden:

	public class Testbed {
		public static void main(String... args) {
			StringCalculator stringCalculator = new StringCalculator();
			String numbers = "1,2,3";
			int expectedSum = 6;
			int actualSum = stringCalculator.add(numbers);
			if(actualSum != expectedSum) {}
				System.out.println("Fehler: Summe von [" +numbers +"] ist " +sum +" Erwartet: " +expectedSum);
			} else {
				System.out.println("Erfolg: Summe von [" +numbers +"] ist " +sum);
			}
		}
	}

Die Ausgabe enthält nun bereits die Information, ob der Test erfolgreich war oder fehlgeschlagen ist.
Das manuelle Vergleichen von erwartetem und tatsächlichem Ergebnis wurde automatisiert.

Um auch andere Eingabestrings zu Testen, wird die main Methode entsprechend erweitert:

	public class Testbed {
		public static void main(String... args) {
			StringCalculator stringCalculator = new StringCalculator();
			String numbers = "1,2,3";
			int expectedSum = 6;
			int actualSum = stringCalculator.add(numbers);
			if(actualSum != expectedSum) {
				System.out.println("Fehler: Summe von [" +numbers +"] ist " +actualSum +" Erwartet: " +expectedSum);
			} else {
				System.out.println("Erfolg: Summe von [" +numbers +"] ist " +actualSum);
			}

			String number = "1";
			int anotherExpectedSum = 1;
			int anotherActualSum = stringCalculator.add(number);
			if(anotherExpectedSum != anotherActualSum) {
				System.out.println("Fehler: Summe von [" +number +"] ist " +anotherActualSum +" Erwartet: " +anotherExpectedSum);
			} else {
				System.out.println("Erfolg: Summe von [" +number +"] ist " +anotherActualSum);
			}

			String numbersWithSpaces = "1, 2, 3";
			int yetAnotherExpectedSum = 6;
			int yetAnotherActualSum = stringCalculator.add(numbersWithSpaces);
			if(yetAnotherExpectedSum != yetAnotherActualSum) {
				System.out.println("Fehler: Summe von [" +numbersWithSpaces +"] ist " +yetAnotherActualSum +" Erwartet: " +yetAnotherExpectedSum);
			} else {
				System.out.println("Erfolg: Summe von [" +numbersWithSpaces +"] ist " +yetAnotherActualSum);
			}
		}
	}

Zur besseren Strukturierung werden die einzelnen Testfälle aus der main Methode in einzelne Methoden extrahiert:

	public class Testbed {
	    public static void main(String... args) {
	        StringCalculator stringCalculator = new StringCalculator();
	        testStringCalculatorWithValidThreeNumberString(stringCalculator, "1,2,3", 6);
	        testStringCalculatorWithValidOneNumberString(stringCalculator, "1", 6);        testStringCalculatorWithValidThreeNumberStringIncludingSpaces(stringCalculator, "1, 2, 3", 6);
	    }

	    private static void testStringCalculatorWithValidThreeNumberString(StringCalculator stringCalculator, String s, int i) {
	        String numbers = "1,2,3";
	        int expectedSum = 6;
	        int actualSum = stringCalculator.add(numbers);
	        if (actualSum != expectedSum) {
	            System.out.println("Fehler: Summe von [" + numbers + "] ist " + actualSum + " Erwartet: " + expectedSum);
	        } else {
	            System.out.println("Erfolg: Summe von [" + numbers + "] ist " + actualSum);
	        }
	    }

	    private static void testStringCalculatorWithValidOneNumberString(StringCalculator stringCalculator, String s, int i) {
	        String numbers = "1";
	        int expectedSum = 6;
	        int actualSum = stringCalculator.add(numbers);
	        if (actualSum != expectedSum) {
	            System.out.println("Fehler: Summe von [" + numbers + "] ist " + actualSum + " Erwartet: " + expectedSum);
	        } else {
	            System.out.println("Erfolg: Summe von [" + numbers + "] ist " + actualSum);
	        }
	    }

	    private static void testStringCalculatorWithValidThreeNumberStringIncludingSpaces(StringCalculator stringCalculator, String s, int i) {
	        String numbers = "1, 2, 3";
	        int expectedSum = 6;
	        int actualSum = stringCalculator.add(numbers);
	        if (actualSum != expectedSum) {
	            System.out.println("Fehler: Summe von [" + numbers + "] ist " + actualSum + " Erwartet: " + expectedSum);
	        } else {
	            System.out.println("Erfolg: Summe von [" + numbers + "] ist " + actualSum);
	        }
	    }
	}

Es fällt auf, das alle Methoden nahezu identischen Code enthalten.
Durch Refaktorisierung erhält man folgende Klasse:

	public class StringCalculatorTester {

		private StringCalculator stringCalculator = new StringCalculator();

		public static void main(String... args) {
			StringCalculatorTester tester = new StringCalculatorTester();

			tester.testStringCalculator("1,2,3", 6);
			tester.testStringCalculator("1", 1);
			tester.testStringCalculator("1, 2, 3", 6);
		}

		private void testStringCalculator(final String inputString, final int expectedSum) {
			int actualSum = stringCalculator.add(inputString);
			if (actualSum != expectedSum) {
				System.out.println("Fehler: Summe von [" + inputString + "] ist " + actualSum + " Erwartet: " + expectedSum);
				} else {
					System.out.println("Erfolg: Summe von [" + inputString + "] ist " + actualSum);
				}
			}
		}

Die Testfälle sind noch unvollständig, können aber leicht durch Erstellung weiterer Testmethoden erweitert werden.


Das Testframework JUnit
-----------------------
JUnit dient zum Erstellen und Ausführen von automatisierten Tests für Java bzw. die JVM.
Es exisiteren Portierungen für verschiedene Plattformen und Programmiersprachen.
Ursprünglich wurde JUnit 1997 von Kent Beck und Erich Gamma entwickelt.
Die aktuelle Version ist 4.12 (Stand 07/2015).
Als alternatives Framework existiert in der Java Welt [TestNG](http://testng.org).

In JUnit3 können eigene Testklassen durch erweitern der Klasse TestCase erstellt.
Testmethoden müssen per Konvention den Prefix test im Namen verwenden.

	public class StringCalculatorTest extends Testcase {
	    public void testAdd_withEmptyString_returnsZero() throws Exception {
			// ...
	    }
	}

Seit JUnit4 entfällt die Vererbung und der Prefix, stattdessen werden Annotationen verwendet:

	public class StringCalculatorTest {
	    @Test
	    public void add_withEmptyString_returnsZero() throws Exception {
			// ...
	    }
	}

JUnit3 Tests können ohne Anpassungen auch unter JUnit4 ausgeführt werden.
Die Wahl der Namen der Testklassen und Methoden ist frei.
Der Klassenname setzt sich in der Regel aus dem Namen der zu testenden Klasse und dem Postfix "Test" zusammen.
Für die Namen der Testmethoden gibt es verschiedene Nameskonventionen:
* testAddMethod()
* UnitOfWork_State_Expected()
Siehe auch [Naming Standards for Test Methods](http://osherove.com/blog/2005/4/3/naming-standards-for-unit-tests.html)

Die Testmethoden müssen public und parameterlos sein, damit sie vom Framework als Test erkannt und ausgeführt werden.

### Umsetzung mit JUnit
Im folgenden wird ein UnitTest der Klasse StringCalculator mit JUnit4 umgesetzt.

Im ersten Schritt muss das Framework von der [Projektwebseite](https://github.com/junit-team/junit/wiki/Download-and-Install) heruntergeladen werden:
* [junit.jar](http://bit.ly/My9IXz)
* [hamcrest-core.jar](http://bit.ly/1gbl25b)
Die jars müssen im Classpath verfügbar gemacht werden.

Anschließend wird die Testklasse erstellt und eine Testmethode hinzugefügt:

	import org.junit.Test;
	import static org.junit.Assert.assertEquals;

	public class StringCalculatorTest {
		@Test
		public void add_withEmptyString_returnsZero() throws Exception {
			StringCalculator underTest = new StringCalculator();
		    int sum = underTest.add("");
		    int expected = 0;
		    assertEquals(expected, sum);
		}
	}

Die Methode ist mit @Test annotiert, um sie dem Framework als Testmethode bekannt zu machen.
Der Name der Testmethoden ist nach folgender Konvention aufgebaut: [Naming Standards for Unit Tests](http://osherove.com/blog/2005/4/3/naming-standards-for-unit-tests.html).

Die Struktur des Testcodes folgt dem AAA-Prinzip: Arrange, Act, Assert.
Innerhalb der Methode wird eine Instanz des zu testenden Objekts erstellt (arrange).
Anschließend wird die zu testende Methode aufgerufen (act)
und schließlich das tatsächliche Ergebnis mit dem erwarteten verglichen (assert).

Der Test wird mit den zugehörigen Dependencies kompiliert
	javac -cp .:junit-4.12.jar StringCalculatorTest.java
und anschließend ausgeführt
	java -cp .:junit-4.12.jar:hamcrest-core-1.3.jar org.junit.runner.JUnitCore StringCalculatorTest

Ausgabe:
	JUnit version 4.12
	.......
	Time: 0,005

	OK (7 tests)

#### Weitere Methoden hinzufügen
* Testmethode hinzufügen:

	import org.junit.Test;
	import static org.junit.Assert.assertEquals;

	public class StringCalculatorTest {
		@Test
		public void add_withEmptyString_returnsZero() throws Exception {
			StringCalculator underTest = new StringCalculator();
		  int sum = underTest.add("");
		  int expected = 0;
		  assertEquals(expected, sum);
		}

	  @Test
	  public void add_withSingleNumber_returnsThatNumber() throws Exception {
			StringCalculator underTest = new StringCalculator();
	    int sum = underTest.add("23");
	    int expected = 23;
	    assertEquals(expected, sum);
		}
	}

* Refaktorisieren: setUp-Methode

	import org.junit.Test;
	import org.junit.BeforeClass;
	import static org.junit.Assert.assertEquals;

	public class StringCalculatorTest {
		private StringCalculator underTest;

		@BeforeClass
		public void setUp() {
		    underTest = new StringCalculator();
		}

		@Test
		public void add_withEmptyString_returnsZero() throws Exception {
		    int sum = underTest.add("");
		    int expected = 0;
		    assertEquals(expected, sum);
		}

	  @Test
	  public void add_withSingleNumber_returnsThatNumber() throws Exception {
	    	int sum = underTest.add("23");
	        int expected = 23;
	        assertEquals(expected, sum);
		}
	}

* Weitere Annotationen:

	import org.junit.Test;
	import org.junit.BeforeClass;
	import org.junit.AfterClass;
	import org.junit.Ignore;
	import static org.junit.Assert.assertEquals;

	public class StringCalculatorTest {
	    private StringCalculator underTest;

	    @BeforeClass
	    public void init() {
	    	underTest = new StringCalculator();
		}

		@Test
		public void add_withEmptyString_returnsZero() throws Exception {
		    int sum = underTest.add("");
		    int expected = 0;
		    assertEquals(expected, sum);
		}

	    @Test
	    public void add_withSingleNumber_returnsThatNumber() throws Exception {
	    	int sum = underTest.add("23");
	        int expected = 23;
	        assertEquals(expected, sum);
		}

	    @Ignore
	    public void add_withTwoNumbers_returnsSum() throws Exception {
	    	int sum = underTest.add("7,13");
	        int expected = 7+13;
	        assertEquals(expected, sum);
		}

		@AfterClass
		public void cleanup() {
			underTest = null;
		}
	}

Die Methode init() wird durch die Annotation @BeforeClass vor der Ausführung der einzelnen Testmethoden aufgerufen.
Entsprechend wird die mit @AfterClass annotierte Methode cleanup() aufgerufen, nachdem alle Testmethoden abgearbeitet wurden.
Die Methodennamen sind seit JUnit4 frei wählbar.
Außerdem gibt es die Annotationen @Before und @After, die vor und nach der Ausführung jeder einzelnen Testmethode aufgerufen werden.
Mit @Ignore kann man eine Testmethode von der Testausführung ausschließen.


Zusammenfassung
---------------
Dieses Dokument hat das Konzept von automatisierten Tests vorgestellt und zunächst an einem einfachen Beispiel demonstriert.
In Code Beispielen wurde eine handgestrickte Testinfrastruktur mit Java Bordmitteln aufgebaut:
Die Testfälle wurden in Methoden gekapselt und innerhalb einer Main-Methode ausgeführt.
Anschließend wurden die Tests auf das Testframework JUnit umgestellt und damit die Kernfunktionalität des Frameworks demonstriert.


Referenzen und weiterführende Links
-----------------------------------
* [JUnit Projektwebsite](http://junit.org/)
* [TestNG](http://testng.org/)
* [Naming Standards for Unit Tests](http://osherove.com/blog/2005/4/3/naming-standards-for-unit-tests.html)
