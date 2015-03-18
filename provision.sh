#!/bin/bash
#
# Copyright (C) 2014 meltmedia (christian.trimble@meltmedia.com)
#
# Licensed under the Apache License, Version 2.0 (the "License");
# you may not use this file except in compliance with the License.
# You may obtain a copy of the License at
#
#         http://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.
#

#
# Provisioning script executed by Vagrant.
#

# add elastic search repository to apt.
wget -O - http://packages.elasticsearch.org/GPG-KEY-elasticsearch | apt-key add -
echo "deb http://packages.elasticsearch.org/elasticsearch/1.4/debian stable main" >> /etc/apt/sources.list

# add source for Java 8
sudo add-apt-repository -y ppa:webupd8team/java

sudo apt-get update

# install Java 8
echo oracle-java8-installer shared/accepted-oracle-license-v1-1 select true | sudo /usr/bin/debconf-set-selections
sudo apt-get install oracle-java8-installer --assume-yes

# get install elasticsearch
apt-get install elasticsearch
sudo update-rc.d elasticsearch defaults 95 10

# scripting setting updates for 1.2.3
echo "script.disable_dynamic: false" >> /etc/elasticsearch/elasticsearch.yml

export ES_MAX_MEM=512m

sudo /etc/init.d/elasticsearch start

# apache
apt-get install apache2 --assume-yes

# install kabana
curl -s https://download.elasticsearch.org/kibana/kibana/kibana-3.0.1.tar.gz | tar xvz --strip-components=1 -C /var/www/html

