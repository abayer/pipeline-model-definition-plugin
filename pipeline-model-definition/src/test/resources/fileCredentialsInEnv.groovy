/*
 * The MIT License
 *
 * Copyright (c) 2017, CloudBees, Inc.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

pipeline {
    environment {
        FILECRED = credentials("fileCred")
        INBETWEEN = "Something ${FILECRED} between"
        CRED_NAME = "cred1"
        CRED1 = credentials("${CRED_NAME}")
    }

    agent any

    stages {
        stage("foo") {
            steps {
                sh 'echo "FILECRED is $FILECRED"'
                sh 'echo "INBETWEEN is $INBETWEEN"'
                sh 'echo $CRED1 > cred1.txt'
            }
        }
        stage("bar") {
            environment {
                OTHERCRED = credentials("otherFileCred")
                OTHER_INBETWEEN = "THIS ${OTHERCRED} THAT"
                SECOND_CRED = "cred2"
                CRED2 = credentials("${SECOND_CRED}")
            }
            steps {
                sh 'echo "OTHERCRED is $OTHERCRED"'
                sh 'echo "OTHER_INBETWEEN is $OTHER_INBETWEEN"'
                sh 'echo $CRED2 > cred2.txt'
                archive "**/*.txt"
            }
        }
    }
}