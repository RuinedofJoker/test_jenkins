pipeline {
    agent any

    environment {
        MAVEN_HOME = tool 'maven-3.9'              // Jenkins中预配置的Maven工具名称
        IMAGE_NAME = 'ruinedofjoker/testjenkins'          // 替换为你的镜像名称
        REGISTRY_CREDENTIALS = 'joker-dockerhub'     // Jenkins凭据ID（Docker Hub用户名密码）
        DEPLOY_HOST = '192.168.1.87'                // 替换为你的远程部署机器IP
        DEPLOY_SSH_CREDENTIALS = 'remote-ssh-key'    // Jenkins凭据ID（SSH Key或用户名密码）
        GIT_REPOS = 'https://github.com/RuinedofJoker/test_jenkins.git'
        GIT_BRANCH = 'main'
    }

    parameters {
        string(name: 'VERSION', defaultValue: 'latest', description: '发布版本号')
        string(name: 'HARBOR', defaultValue: 'joker174.chat:85', description: 'HARBOR地址')
    }

    stages {
        stage('Clone Source Code') {
            steps {
                deleteDir()
                print('拉取项目代码...\n分支：' + env.GIT_BRANCH + '\n仓库：' + env.GIT_REPOS)
                git branch: env.GIT_BRANCH, url: env.GIT_REPOS
            }
        }
        stage('Maven Build') {
            steps {
                print('开始使用 Maven 编译项目...')
                sh """
                    mvn clean install -Dmaven.test.skip=true
                """
            }
        }
        stage('Build and Push Docker Image') {
            steps {
                script {
                    sh """
                        mkdir releasesDir
                        cp ./target/*.jar ./releasesDir/
                    """
                    def imageFullName = "${params.HARBOR}/${env.IMAGE_NAME}:${params.IMAGE_TAG}"
                    print "🐳 构建 Docker 镜像: ${imageFullName}"
                    sh """
                        cd ./releasesDir
                        cp ../Dockerfile ./
                        docker build -t ${imageFullName} .
                    """
                    print "🔐 登录 Docker Hub 并推送镜像"
                    withCredentials([usernamePassword(
                            credentialsId: env.REGISTRY_CREDENTIALS,
                            usernameVariable: 'DOCKER_USER',
                            passwordVariable: 'DOCKER_PASS'
                    )]) {
                        sh """
                            echo "$DOCKER_PASS" | docker login ${params.HARBOR} -u "$DOCKER_USER" --password-stdin
                            docker push ${imageFullName}
                            docker logout
                        """
                    }
                }
            }
        }
    }
}