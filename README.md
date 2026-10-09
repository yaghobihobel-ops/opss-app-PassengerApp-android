# opss — Passenger (Android)

اپلیکیشن اندروید مسافرِ پلتفرم چندسرویسی **opss** (سورس v3Cube، سال ۲۰۲۱).

| مورد | مقدار |
| --- | --- |
| Package | `com.alaadcin.user` |
| minSdk / targetSdk | 17 / 31 |
| Gradle / AGP | 5.4.1 / 3.5.2 |
| JDK در CI | **17 برای sdkmanager** · **11 برای Gradle** |
| Flavor | `prod` |
| Backend | `https://taxi.ecardo.ir/` |

## چرا JDK 11 و نه 8

سورس برای JDK 8 نوشته شده بود، ولی annotation processor زبان Kotlin با JDK 8
روی این پروژه می‌شکند (`AssertionError: annotationType(): unrecognized Attribute
name MODULE`). Gradle 5.4.1 هم با JDK 17 سازگار نیست. بنابراین CI از JDK 11
استفاده می‌کند. **هیچ نسخه‌ای از Gradle، AGP، Kotlin یا targetSdk ارتقا نیافته است.**

## قوانین کاری این ریپو

این قوانین برای هر کسی که روی این پروژه کار می‌کند الزامی است:

1. **کار فقط از راه GitHub.** بیلد، تست و انتشار باید روی runner گیت‌هاب انجام
   شود. روی سیستم محلی `gradlew` اجرا نشود.

2. **دیباگ فقط روی شاخهٔ `fix/stable`.** به `main` فقط کدی می‌رسد که روی شاخهٔ
   دیباگ سبز شده. هر push به `main` یک نسخهٔ جدید و یک Release عمومی می‌سازد، پس
   `main` باید همیشه قابل انتشار بماند.

3. **هر push به `main` نسخه را یک پله بالا می‌برد** (`0.0.1` → `0.0.2` → …) و
   `versionCode` یکی اضافه می‌کند. تگ Release همیشه `v<versionName>` است و باید
   با نسخهٔ داخل خود APK یکی باشد.

4. **نسخه نهایی فقط در Release است.** فایل artifact برای مصرف داخلی و تست
   است؛ چیزی که کاربر دانلود می‌کند از بخش Release می‌آید.

5. **ارتقای ابزار ممنوع است** مگر با تأیید صریح. Gradle، AGP، Kotlin، JDK،
   minSdk و targetSdk دست‌نخورده می‌مانند.

6. **هیچ توکن، کلید یا keystore در مخزن.** اسرار فقط از GitHub Actions Secrets
   می‌آیند و در فایل یا لاگ نوشته نمی‌شوند.

7. **هر تغییر باید در یک کامیت باشد** با پیام `fix(ci): <علت>` که علت واقعی را
   توضیح می‌دهد، نه علامت‌ها.

## خط لولهٔ CI

`.github/workflows/build.yml` چهار job دارد:

| Job | کار |
| --- | --- |
| `build` | نصب NDK، بیلد `assembleProdDebug`، خواندن badging، آپلود APK |
| `smoke-test` | نصب روی امولاتور، اجرا، اسکن `FATAL EXCEPTION` و `ANR` |
| `runtime-logs` | نصب، اجرای ۴۵ ثانیه، گرفتن `logcat` و اسکرین‌شات |
| `release` | فقط روی `main`: بالا بردن نسخه، commit، ساخت Release |

### اجرای دستی

از تب **Actions** → **Build and Release** → **Run workflow** و در فیلد `ref`
شاخهٔ `fix/stable` را وارد کنید.

## نصب روی گوشی

بیلدهای فعلی **debug** هستند و با کلید استاندارد debug اندروید امضا شده‌اند.

اگر قبلاً نسخهٔ اصلی فروشنده روی گوشی نصب بوده، **اول باید آن را حذف کنید**.
اندروید اجازه نمی‌دهد اپی با امضای متفاوت روی اپ دیگری نصب شود و پیام
`there was a problem while parsing the package` یا
`App not installed` می‌دهد.

## آدرس بک‌اند

آدرس سرور یک جا تعریف شده و از خط فرمان قابل تغییر است:

```bash
./gradlew assembleProdDebug -PSERVER_BASE_URL=https://example.com/
```

مقدار پیش‌فرض `https://taxi.ecardo.ir/` است و در `BuildConfig.SERVER_BASE_URL`
و `BuildConfig.API_BASE_URL` قرار می‌گیرد.

## فارسی و راست‌چین

`app/src/main/res/values-fa/strings.xml` وجود دارد و `android:supportsRtl="true"`
در مانیفست فعال است. ترجمهٔ کامل رشته‌ها هنوز انجام نشده؛ رشته‌های ترجمه‌نشده به
زبان پیش‌فرض برمی‌گردند تا متن شکسته نمایش داده نشود.

## کاهش حجم APK

- `abiFilters 'armeabi-v7a', 'arm64-v8a'` — معماری‌هایی که هیچ گوشی واقعی از آن‌ها
  استفاده نمی‌کند حذف می‌شوند.
- نتیجه: حجم APK از حدود ۵۵MB به حدود ۴۰MB کاهش یافته است.

## نکات فنی مهم

- **`jcenter()`** تعطیل شده و با `mavenCentral()` به‌همراه آینهٔ
  `maven.aliyun.com/repository/jcenter` جایگزین شده است.
- **NDK 22** باید نصب باشد؛ فایل‌های `.so` پیش‌ساخته با همان نسخه ساخته شده‌اند.
- **امضای release** به keystore فروشنده اشاره می‌کند که در این ریپو نیست؛
  به همین دلیل شرطی است و در نبود آن از کلید debug استفاده می‌شود.