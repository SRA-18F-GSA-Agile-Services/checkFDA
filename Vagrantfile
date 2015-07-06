# -*- mode: ruby -*-
# vi: set ft=ruby :

# Vagrantfile API/syntax version. Don't touch unless you know what you're doing!
VAGRANTFILE_API_VERSION = "2"

VM_BOX = "trusty-server-cloudimg-amd64-vagrant"
VM_BOX_URL = "https://cloud-images.ubuntu.com/vagrant/trusty/current/trusty-server-cloudimg-amd64-vagrant-disk1.box"

Vagrant.configure(VAGRANTFILE_API_VERSION) do |config|

    config.vm.box = VM_BOX
    config.vm.box_url = VM_BOX_URL

    config.vm.network "forwarded_port", guest: 8080, host: 8888
#    config.vm.network "forwarded_port", guest: 3306, host: 3306

    config.vm.network "private_network", ip: "192.168.33.10"
#    config.vm.synced_folder ".", "/vagrant"   # enable this to NFS mount the host current directory from within the guest VM.

    config.vm.provider "virtualbox" do |vb|
      vb.customize ["modifyvm", :id, "--memory", "2048"]
    end

    config.vm.provision "ansible" do |ansible|
      ansible.host_key_checking = false
      ansible.playbook = "playbooks/standalone.yml"
      ansible.sudo = true
      ansible.verbose = "vvvv"
    end
end
