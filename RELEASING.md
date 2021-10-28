# Releasing

### Linux

- Set `VersionName` variable
```
export VersionName=<VersionName>
```

- Create a release branch
```
git checkout master
git pull --recurse-submodule
cd kotlin-components
git checkout master
git pull
cd ..
git checkout -b release_$VersionName
```

- Update `versionName` (remove `-SNAPSHOT`) and `versionCode` in root project's `build.gradle.kts` file
```kotlin
kmpPublish {
    setupRootProject(
        versionName = "<VersionName>",
        versionCode = `<VersionCode>`,
    )
}
```

- Update `version` in project's `README.md` documentation

- Update `CHANGELOG.md`

- Commit Changes
```
git add --all
git commit -S -m "Prepare $VersionName release"
git tag -s $VersionName -m "Release v$VersionName"
```

- Make sure you have valid credentials in `~/.gradle/gradle.properties`
```groovy
mavenCentralUsername=MyUserName
mavenCentralPassword=MyPassword
```

- Make sure you have GPG gradle config setup in `~/.gradle/gradle.properties` for signing
```
signing.gnupg.executable=gpg
signing.gnupg.useLegacyGpg=true
signing.gnupg.homeDir=/path/to/.gnupg/
signing.gnupg.optionsFile=/path/to/.gnupg/gpg.conf
signing.gnupg.keyName=0x61471B8AB3890961
```

- Make sure GPG is picking up YubiKey to sign releases
```
gpg --card-status
```

- Run publishing script
```
./kotlin-components/scripts/publish.sh --host linux
```

- Push to repo
```
git push origin $VersionName
git push -u origin release_$VersionName
```

### Windows

- Spin up VM of Windows 10

- Open Git Bash and navigate to project

- Pull latest code from release branch
```
git checkout master
git pull --recurse-submodule
cd kotlin-components
git checkout master
git pull
cd ..
git checkout release_<VersionName>
git pull
```

- Make sure you have valid credentials in `~/.gradle/gradle.properties`
```
mavenCentralUsername=MyUserName
mavenCentralPassword=MyPassword
```

- Make sure `KMP_TARGETS` is set in `~/.gradle/gradle.properties`
```
KMP_TARGETS=ANDROID,MINGW_X64,MINGW_X86
```

- Make sure you have GPG gradle config setup in `~/.gradle/gradle.properties` for signing
```
signing.gnupg.executable=gpg
signing.gnupg.useLegacyGpg=true
signing.gnupg.homeDir=/path/to/.gnupg/
signing.gnupg.optionsFile=/path/to/.gnupg/gpg.conf
signing.gnupg.keyName=0x61471B8AB3890961
```

- Enable USB pass through of the YubiKey to Windows VM to allow for signing

- Open powershell

- Make sure GPG is picking up YubiKey to sign releases
```
gpg --card-status
```

- Run publishing script
```
./kotlin-components/scripts/publish.sh --host mingw
```

- Paste output into powershell and run it (because Windows doesn't like Bash + GPG and I'm too lazy to fix)

- Disable USB pass through

### Macos

- Spin up VM of Macos BigSur and ensure USB pass through worked for the YubiKey
    - Should ask for PIN to login

- Sign a random `.txt` file (gpg tty for YubiKey PIN + gradle build don't mix)
```
cd ~/Documents
gpg --sign --armor --detach hello.txt
```

- Pull latest code from release branch
```
git checkout master
git pull --recurse-submodule
cd kotlin-components
git checkout master
git pull
cd ..
git checkout release_<VersionName>
git pull
```

- Make sure you have valid credentials in `~/.gradle/gradle.properties`
```
mavenCentralUsername=MyUserName
mavenCentralPassword=MyPassword
```

- Make sure `KMP_TARGETS` is set in `~/.gradle/gradle.properties`
```
KMP_TARGETS=ANDROID,IOS_ALL,IOS_ARM32,IOS_ARM64,IOS_X64,IOS_SIMULATOR_ARM64,MACOS_ARM64,MACOS_X64,TVOS_ALL,TVOS_ARM64,TVOS_X64,TVOS_SIMULATOR_ARM64,WATCHOS_ALL,WATCHOS_ARM32,WATCHOS_ARM64,WATCHOS_X64,WATCHOS_X86,WATCHOS_SIMULATOR_ARM64
```

- Make sure you have GPG gradle config setup in `~/.gradle/gradle.properties` for signing
```
signing.gnupg.executable=gpg
signing.gnupg.useLegacyGpg=true
signing.gnupg.homeDir=/path/to/.gnupg/
signing.gnupg.optionsFile=/path/to/.gnupg/gpg.conf
signing.gnupg.keyName=0x61471B8AB3890961
```

- Run publishing script
```
./kotlin-components/scripts/publish.sh --host darwin
```

### Linux
- Close publications (Don't release yet)
    - Login to Sonatype OSS Nexus: [oss.sonatype.org](https://oss.sonatype.org/#stagingRepositories)
    - Click on **Staging Repositories**
    - Select all Publications
    - Click **Close** then **Confirm**
    - Wait a bit, hit **Refresh** until the *Status* changes to *Closed*

- Check Publication
```
./kotlin-components/scripts/publish.sh --check-publication
```

### Windows
- Check Publication
```
./kotlin-components/scripts/publish.sh --check-publication
```

### Macos
- Check Publication
```
./kotlin-components/scripts/publish.sh --check-publication
```

### Linux
- **Release** publications from Sonatype OSS Nexus StagingRepositories manager

- Merge release branch to `master`
```
git checkout master
git pull
git merge --no-ff -S release_$VersionName
```

- Update `versionName` (add `-SNAPSHOT`) and `versionCode` in root project's `build.gradle.kts` file
```kotlin
kmpPublish {
    setupRootProject(
        versionName = "<VersionName + 1>-SNAPSHOT",
        versionCode = `<VersionCode + 1>`,
    )
}
```

- Commit changes
```
git add --all
git commit -S -m "Prepare for next development iteration"
```

- Push Changes
```
git push
```

- Delete release branch on GitHub

- Delete local release branch
```
git branch -D release_$VersionName
```

### Windows & Macos

- Checkout master
```
git checkout master
git pull --recurse-submoudle
cd kotlin-components
git checkout master
git pull
cd ..
```

- Delete local release branch
```
git branch -D release_<VersionName>
```

- Shutdown VMs (if not needed anymore)

### Linux

- Wait for releases (should be 3 of them) to become available on [MavenCentral](https://repo1.maven.org/maven2/io/matthewnelson/kotlin-components/)
- Draft new release on GitHub
    - Enter the release name <VersionName> as tag and title
    - Have the description point to the changelog
