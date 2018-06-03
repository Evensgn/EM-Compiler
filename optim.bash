set -e
cd "$(dirname "$0")"
cat > testcase/test.mx   # save everything in stdin to test.mx
java -classpath ./lib/antlr-4.7.1-complete.jar:./bin com.evensgn.emcompiler.Main testcase/test.mx -o out.asm
cat out.asm