#include <cstdio>
#include <cstring>
#include <malloc.h>

char* __builtin_string_concat(char *str1, char *str2) {
    int len1 = *((long*) str1), len2 = *((long*) str2);
    char *catStr = (char*) malloc(9 + len1 + len2);
    *((long*) catStr) = len1 + len2;
    str1 += 8;
    str2 += 8;
    catStr += 8;
    int now = -1;
    for (int i = 0; i < len1; ++i)
        catStr[++now] = str1[i];
    for (int i = 0; i < len2; ++i)
        catStr[++now] = str2[i];
    catStr[now + 1] = 0;
    return catStr - 8;
}

int __builtin_string_equal(char *str1, char *str2) {
    return strcmp(str1 + 8, str2 + 8) == 0;
}

int __builtin_string_inequal(char *str1, char *str2) {
    return strcmp(str1 + 8, str2 + 8) != 0;
}

int __builtin_string_less(char *str1, char *str2) {
    return strcmp(str1 + 8, str2 + 8) < 0;
}

int __builtin_string_less_equal(char *str1, char *str2) {
    return strcmp(str1 + 8, str2 + 8) <= 0;
}

void print(char *str) {
    printf("%s", str + 8);
}

void println(char *str) {
    puts(str + 8);
}

void printInt(int num) {
    if (num == 0) putchar('0');
    if (num < 0) {
        num = -num;
        putchar('-');
    }
    int digits[10], len = 0;
    while (num > 0) {
        digits[len++] = num % 10;
        num /= 10;
    }
    for (int i = len - 1; i >= 0; --i)
        putchar('0' + digits[i]);
}

void printlnInt(int num) {
    if (num == 0) putchar('0');
    if (num < 0) {
        num = -num;
        putchar('-');
    }
    int digits[10], len = 0;
    while (num > 0) {
        digits[len++] = num % 10;
        num /= 10;
    }
    for (int i = len - 1; i >= 0; --i)
        putchar('0' + digits[i]);
    putchar('\n');
}

char* getString() {
    char *str = (char*) malloc(266);
    scanf("%s", str + 8);
    *((long *) str) = strlen(str + 8);
    return str;
}

int getInt() {
    char c = getchar();
    bool neg = false;
    while (c < '0' || c > '9')
    {
        if (c == '-') neg = true;
        c = getchar();
    }
    int num = c - '0'; c = getchar();
    while (c >= '0' && c <= '9')
    {
        num = num * 10 + c - '0';
        c = getchar();
    }
    if (neg) return -num;
    return num;
}

char* toString(int num) {
    int neg = 0, len = 0;
    if (num < 0) {
        neg = 1;
        num = -num;
    }
    int digits[10];
    if (num == 0) {
        digits[++len] = 0;
    }
    else {
        while (num != 0) {
            digits[++len] = num % 10;
            num /= 10;
        }
    }
    char *str = (char*) malloc(len + neg + 9);
    *((long*) str) = len + neg;
    str += 8;
    str[len + neg] = 0;
    if (neg) str[0] = '-';
    for (int i = 0; i < len; ++i)
        str[i + neg] = digits[len - i] + '0';
    return str - 8;
}

char* __member___string_substring(char *str, int l, int r) {
    int len = r - l + 1;
    char *subStr = (char*) malloc(9 + len);
    *((long*) subStr) = len;
    str += 8 + l;
    subStr += 8;
    for (int i = 0; i < len; ++i)
        subStr[i] = str[i];
    subStr[len] = 0;
    return subStr - 8;
}

int __member___string_parseInt(char *str) {
    str += 8;
    bool neg = false;
    int i = 0;
    while (str[i] < '0' || str[i] > '9')
    {
        if (str[i++] == '-') neg = true;
    }
    int num = str[i++] - '0';
    while (str[i] >= '0' && str[i] <= '9')
    {
        num = num * 10 + str[i++] - '0';
    }
    if (neg) return -num;
    return num;
}

int __member___string_ord(char *str, int idx) {
    idx += 8;
    return str[idx];
}