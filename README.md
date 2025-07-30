# File Filter

This project is a command-line utility written in Java that processes text files by classifying each line into one of three types: **integers**, **floating-point numbers**, or **strings**. The program then writes each type into separate output files and optionally generates summary or full statistics.

---
## 🛠 Features
-	✅ Accepts command-line arguments for:
    -	Input file paths
    -	Output file directory
    -	Output filename prefix
    -	Append vs overwrite mode
    -	Short or full statistics
-	✅ Reads multiple input files line by line
-	✅ Classifies each line as:
    -	Integer (e.g. 42)
    -	Float (e.g. 3.14, 1e-3)
    -	String (everything else)
-	✅ Outputs results into:
    -	integers.txt
    -	floats.txt 
    -	strings.txt
-	✅ Generates:
    -	Short stats: line counts
    -	Full stats:
        -	Min / Max / Sum / Average for numbers 
        -	Line length stats for strings
-	✅ Handles:
    -   Missing files
    -	Invalid input
    -	Empty or malformed lines

---

## 📦 Build Instructions

### Prerequisites
-	Java 16 or above
-	Apache Maven 3.9.1 or above
### External libraries
-   Apache Commons CLI 1.5.0

### Clone and Build
<pre><code>```bash
git clone repo-url
cd file-filter
mvn clean package
```
</code></pre>
The compiled .jar file will be in the target/ folder — likely named:
<pre><code>```bash
target/util-jar-with-dependencies.jar
```
</code></pre>

---

## 🚀 Usage
<pre><code>```bash
java -jar target/util-jar-with-dependencies.jar [options] file1.txt file2.txt ...
```
</code></pre>
### Options

Flag | Description | Example
----- |------------|--------
-o	 |Output path prefix (folder or path)	|-o ./output/
-p	|Filename prefix for all output files |	-p my_
-a	|Append to existing files instead of overwriting	|-a
-s	|Show short statistics (line counts only)	|-s
-f	|Show full statistics (min, max, sum, avg, etc.)	|-f

---

## 📂 Output

Depending on the content of your input files, the following files are generated:
-	integers.txt
-	floats.txt
-	strings.txt

With optional prefix and path based on -o and -p.

---

## ⚠️ Error Handling
-	Skips missing or unreadable input files
-	Skips blank lines
-	Ignores malformed numbers during stats
-	Automatically creates the output directory (if needed)
-	Prints errors to stderr, not stack traces

---

Task completed as part of the application to the SHIFT Java Developer course by КоронаТех.
Author: Nurassyl Mukan