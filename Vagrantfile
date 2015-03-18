# -*- mode: ruby -*-
# vi: set ft=ruby :

Vagrant.configure("2") do |config|
  config.vm.box = "ubuntu/trusty64"
  config.vm.provider "virtualbox" do |v|
    v.memory = 2048
  end
  config.vm.network :forwarded_port, guest: 8080, host: 8180
  config.vm.network :forwarded_port, guest: 8081, host: 8181
  config.vm.network :forwarded_port, guest: 9200, host: 9200
  config.vm.network :forwarded_port, guest: 9300, host: 9300
  config.vm.network :forwarded_port, guest: 80, host: 9201
  config.vm.provision :shell, :path => "provision.sh"
end
