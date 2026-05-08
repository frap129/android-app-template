# AGENTS.md

## Project Overview

Multi-module Android app built from the [Android App Template](https://github.com/frap129/android-app-template pattern). Kotlin, Jetpack Compose, Koin DI, Jetpack Navigation with modular `Destination`-based routing.

- Root project name: `AppTemplate`
- Package: `dev.maples.template`
- Min SDK 32, Compile SDK 36, Java 21

## Module Architecture

```
app/              -> Application entry point (MainActivity only + destination list)
core/lifecycle/   -> MainApplication (Koin setup, Timber), TemplateActivity (deep-link support)
core/ui/          -> Theme, Destination class, AppFrame composable, common UI
core/util/        -> Static utility functions
feature/          -> Feature modules (one per user-facing screen/flow)
build-logic/convention/ -> Gradle convention plugins
```

### `app/` — WARNING

**Never add new Activities, classes, or logic to `app/`.** The only change allowed here is adding new `Destination` entries to the `destinations` list in `MainActivity`. All screens, logic, and UI belong in `feature` modules.

### `core/` — Shared infrastructure

Core modules provide reusable, cross-cutting functionality consumed by multiple feature modules. Examples: repositories, API clients, DI setup, common composables, theme, navigation primitives, utility functions. A core module should never contain user-facing screens.

### `feature/` — User-facing screens and flows

Each feature module implements one screen, a set of related screens, or a self-contained user flow. A feature module exports exactly one `Destination` object (its navigation entry point). Features may navigate to each other at runtime but must **never** depend on another feature module at build time. Features depend only on `core` modules and external libraries.

**Key rules:**

- DI is centralized in `core/lifecycle/MainApplication.kt` (Koin modules: `appModule`, `repoModule`, `viewModelModule`). Register new ViewModels and repositories there.

## Convention Plugins (build-logic)

Common dependencies (core-ktx, coroutines, lifecycle, Koin BOM, Timber) are added automatically by `build-logic/convention/src/main/kotlin/dev/maples/build/Android.kt` via `configureAndroidDependencies()`. You do NOT need to declare these in individual module `build.gradle.kts` files — they come for free with the convention plugins. Only add module-specific external dependencies manually.

Use version catalog aliases to apply these in `build.gradle.kts`:

| Plugin alias                               | Purpose                               |
| ------------------------------------------ | ------------------------------------- |
| `libs.plugins.modular.android.application` | App modules (includes signing config) |
| `libs.plugins.modular.android.library`     | Library modules                       |
| `libs.plugins.modular.android.test`        | Test modules                          |
| `libs.plugins.modular.compose`             | Adds Compose compiler + dependencies  |
| `libs.plugins.modular.dokka`               | Per-module documentation              |

New module minimal `build.gradle.kts`:

```kotlin
plugins {
    alias(libs.plugins.modular.android.library)
    alias(libs.plugins.modular.compose)  // if uses Compose
}
android {
    namespace = "core.mymodule"  // or "feature.myfeature"
}
```

After adding a module, register it in `settings.gradle.kts`.

## Developer Commands

```bash
# Format check (CI runs this)
./gradlew spotlessCheck --no-configuration-cache

# Auto-fix formatting
./gradlew spotlessApply --no-configuration-cache

# Build
./gradlew assembleDebug

# Generate docs (HTML + Markdown into docs/)
./gradlew generateDocs

# Run unit tests
./gradlew test

# Run instrumented tests
./gradlew connectedAndroidTest
```

**Important:** Spotless requires `--no-configuration-cache` flag (configuration cache is enabled globally in `gradle.properties` but Spotless is incompatible with it).

## Code Style

- Enforced by **Spotless** with **ktlint** (android_studio style), configured via `.editorconfig`
- 4-space indent, max 140 char line length
- No wildcard imports (star imports disabled)
- Composable functions may use PascalCase (ktlint exception configured)
- Experimental ktlint rules are enabled
- Always run `./gradlew spotlessApply --no-configuration-cache` before committing

## Commit & PR Conventions

Strict **Conventional Commits** enforced by CI (`prValidator.js`):

- Format: `type(scope): description` (no trailing punctuation)
- Valid types: `build`, `ci`, `docs`, `feat`, `fix`, `perf`, `refactor`, `style`, `test`, `chore`, `revert`
- Subject max 50 chars (72 with `allow-long-subject` label)
- PR body must include `ticket: <value>` trailer above a `****` separator
- Body lines max 72 chars (above separator)

## Signing

Optional `keystore.properties` in project root. Supports per-variant overrides (e.g. `release.storeFile`). If absent, default debug signing is used. Never commit this file.

## Dependencies & Version Catalog

All dependency versions live in `gradle/libs.versions.toml`. Use catalog aliases in build files rather than hardcoded coordinates.

Key libraries: Compose BOM, Koin (BOM), Navigation Compose, Timber, Coroutines, Lifecycle, Accompanist.

## Files to Never Commit

- `keystore.properties`
- `local.properties`
- `*.keystore` / `*.jks`
