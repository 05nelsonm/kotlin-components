# Releasing

### Linux

- Create a release branch
```
git checkout master
git pull
git checkout -b release_<VersionName>
git submodule update --init
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

- Update the Kotlin Version compatibility matrix in project's `README.md` documentation

- Update `CHANGELOG.md`

- Commit Changes
```
git add --all
git commit -S -m "Prepare <VersionName> release"
git tag -s $VersionName -m "Release v<VersionName>"
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

- Disable YubiKey touch for signing
```
ykman openpgp keys set-touch sig off
```

- Run publishing script
```
./kotlin-components/scripts/publish.sh --host linux
```

- Push to repo (to publish from macOS)
```
git push origin <VersionName>
```

### Macos

- Spin up VM of macOS BigSur and ensure USB pass through worked for the YubiKey
    - Should ask for PIN to log in

- Sign a random `.txt` file (gpg tty for YubiKey PIN + gradle build don't mix)
```
cd ~/Documents
gpg --sign --armor --detach hello.txt
```

- Pull the latest code from release branch
```
git checkout master
git pull
git checkout release_<VersionName>
git submodule update --init
```

- Make sure you have valid credentials in `~/.gradle/gradle.properties`
```
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

- Run publishing script
```
./kotlin-components/scripts/publish.sh --host darwin
```

### Linux
- Re-enable YubiKey touch for signing
```
ykman openpgp keys set-touch sig on
```

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

- Push Tag
```
git push -u origin release_<VersionName>
```

- Delete release branch on GitHub

- Delete local release branch
```
git branch -D release_$VersionName
```

### Macos

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
