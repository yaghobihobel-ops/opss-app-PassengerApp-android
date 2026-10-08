# opss — Passenger Android

اپلیکیشن اندروید مسافرِ پلتفرم چندسرویسی **opss** (سورس v3Cube، سال ۲۰۲۱).

| مورد | مقدار |
| --- | --- |
| Package | `com.alaadcin.user` |
| minSdk / targetSdk | 17 / 31 |
| Gradle / AGP | 5.4.1 / 3.5.2 |
| JDK | **11** (Gradle 5.4.1 با JDK 17 و JDK 8 هر دو خطا می‌دهند) |
| Flavor اصلی | `prod` |
| خروجی فعلی | `debug` (تا زمان نهایی‌شدن پروژه) |

## ساختار

```
build.gradle              تنظیمات سطح ریشه و پلاگین‌ها
settings.gradle           ماژول :app
gradle/wrapper/           Gradle Wrapper 5.4.1
app/build.gradle          تنظیمات ماژول اپ
app/src/main/             سورس و منابع
```

## CI/CD — گردش کار خودکار

هر push روی شاخهٔ `main` این گیت‌اکشن را اجرا می‌کند:

1. **افزایش نسخه** — نسخه به‌صورت استاندارد یک واحد بالا می‌رود
   (`versionName` مثل `0.0.1` → `0.0.2` و `versionCode` یکی اضافه می‌شود)
2. **کامیت افزایش نسخه** به `main`
3. **بیلد** `assembleProdDebug` با JDK 8
4. **انتشار** APK روی Release با تگ `v<versionName>`

هر Release شامل سه بخش است: **چه چیزی و چرا تغییر کرد** (لیست کامیت‌ها)، **فایل‌های تغییرکرده**، و APK برای دانلود.

اجرای دستی: تب **Actions** → **Build and Release** → **Run workflow**

### کارکردهای کمکی

| Workflow | کار |
| --- | --- |
| `prepare-project.yml` | بازیابی Gradle Wrapper و اصلاح مخازن مردهٔ Gradle |
| `import-release.yml` | انتقال سورس از فایل زیپِ یک Release به شاخهٔ `main` |

## بیلد محلی

نیازمند JDK 8 و Android SDK با platform 31 و build-tools 31.0.0:

```bash
./gradlew assembleProdDebug
```

## یادداشت امضا (signing)

کانفیگ امضای release به مسیر keystore روی ماشین فروشنده اشاره می‌کند که در این مخزن نیست. به همین دلیل امضا **شرطی** شده است: اگر keystore موجود نباشد، بیلد با keystore پیش‌فرض debug اندروید انجام می‌شود. برای بیلد release واقعی، مسیر را در `app/build.gradle` تغییر دهید.

## نکات مربوط به وابستگی‌ها

این پروژه از سال ۲۰۲۱ است و برخی مخازن Maven آن‌زمان امروز در دسترس نیستند:

- **`jcenter()`** تعطیل شده → جایگزین شده با `mavenCentral()` و آینهٔ `maven.aliyun.com/repository/jcenter`
- **Splunk Mint** و **Fabric** دیگر پاسخ نمی‌دهند → حذف شدند (کدی از آن‌ها استفاده نمی‌کرد)
- **`com.trafi:anchor-bottom-sheet-behavior`** فقط از طریق `jitpack` قابل دریافت است