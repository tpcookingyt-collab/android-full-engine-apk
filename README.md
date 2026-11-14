# Android Full Engine APK

Project Android sạch, native Kotlin, build APK tự động bằng GitHub Actions.
Đây là một game/app engine 2D chạy hoàn toàn trên Android — không cần executor, không tiêm vào app khác.

## Engine có sẵn

| Module | File | Chức năng |
|---|---|---|
| Core Loop | `engine/core/GameEngine.kt`, `GameView.kt` | Vòng lặp game bằng SurfaceView + thread riêng, FPS counter, pause/resume theo lifecycle |
| Scene | `engine/core/Scene.kt`, `SceneManager.kt` | Quản lý nhiều màn chơi, chuyển scene |
| ECS | `engine/ecs/` | Entity – Component – System (`World`, `Transform`, chuyển động) |
| Physics | `engine/physics/` | Vec2, AABB, Circle, trọng lực, va chạm AABB–AABB, Circle–Circle, AABB–Circle |
| Graphics | `engine/graphics/` | Camera2D, Sprite (vẽ bằng Canvas), Animation theo frame |
| Input | `engine/input/InputManager.kt` | Chạm đa điểm, kéo/thả, vị trí touch theo tọa độ thế giới |
| Audio | `engine/audio/AudioEngine.kt` | SoundPool phát hiệu ứng, nhạc nền bằng MediaPlayer |
| Assets | `engine/assets/AssetManager.kt` | Load + cache Bitmap từ thư mục `assets/` |
| Storage | `engine/storage/SaveManager.kt` | Lưu cấu hình / điểm cao bằng SharedPreferences |
| Network | `engine/network/NetworkClient.kt` | GET/POST HTTP bằng HttpURLConnection, callback background thread |
| UI / HUD | `engine/ui/Hud.kt` | Vẽ FPS, điểm số, text trên canvas |
| Demo Game | `game/DemoScene.kt` | Scene mẫu: bóng nảy vật lý, điều khiển bằng chạm, dùng đủ ECS + Physics + Camera + HUD |

## Cấu trúc

```text
app/src/main/java/com/freavity/androidengine/
├── MainActivity.kt
├── engine/
│   ├── core/  ecs/  physics/  graphics/  input/
│   ├── audio/ assets/ storage/ network/ ui/
└── game/DemoScene.kt
.github/workflows/android.yml   # CI build APK
```

## Build APK

### Cách 1 — GitHub Actions (không cần cài gì)
1. Push code lên nhánh `main`.
2. Vào tab **Actions** → run `Android CI` sẽ tự chạy.
3. Tải APK ở mục **Artifacts**: `app-debug` và `app-release-unsigned`.
4. Muốn tạo Release: push tag dạng `v1.0.0`, workflow sẽ tự đính kèm APK vào Release.

### Cách 2 — Android Studio
Mở thư mục này bằng Android Studio, đợi Gradle sync, chọn **Build > Build APK(s)**.

### Cách 3 — Dòng lệnh
Cần JDK 17 + Android SDK (compileSdk 34):

```bash
gradle wrapper --gradle-version 8.7
./gradlew assembleDebug assembleRelease
```

APK ra ở `app/build/outputs/apk/debug/` và `app/build/outputs/apk/release/`.

## Yêu cầu
- JDK 17, Gradle 8.7, Android Gradle Plugin 8.5.2, Kotlin 1.9.24
- minSdk 24 (Android 7.0), targetSdk / compileSdk 34

## License
MIT
