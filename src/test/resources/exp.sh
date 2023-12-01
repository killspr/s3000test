#!/usr/bin/expect
sleep 30
#connect to vm
spawn ssh user@192.168.202.25

expect "password:"
sleep 1
send "12345678\r"
expect "$ "

send "export value=\$(bash /home/user/script.sh)\r"
expect "$ "

send "echo \$value\r"
expect "$ "

#load new image
send "docker load -i \$value\r"
expect "$ "

#create new volume
send "docker volume create armS3000\r"

#run new container
send "docker run -d --publish 20080:80 --publish 20043:443 --publish 20497:64497/udp --volume \"armS3000:/persist\" --name \"armS3000\" \${value::-19}:\${value:22:-7}\r"
expect "$ "

#accept license
send "bash /home/user/licence-accept.sh\r"
expect "$ "

send "exit\r"
expect eof

#return control
interact