
package comq.russia.app_danhbadienthoai.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import comq.russia.app_danhbadienthoai.R;
import comq.russia.app_danhbadienthoai.model.Person;


/**
 * Created by VLADIMIR PUTIN on 2/9/2018.
 */


public class ArrayAdapter extends android.widget.ArrayAdapter<Person> {

    Context context;
    int resource;
    ArrayList<Person> arrayList;


    public ArrayAdapter(@NonNull Context context, int resource, @NonNull ArrayList<Person> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
        this.arrayList = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder viewHolder = new ViewHolder();
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_list, parent, false);
            viewHolder.imageView = convertView.findViewById(R.id.img_avatar);
            viewHolder.textName = convertView.findViewById(R.id.tv_name);
            viewHolder.textTel = convertView.findViewById(R.id.tv_number);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        Person person = arrayList.get(position);
        viewHolder.textName.setText(person.getName());
        viewHolder.textTel.setText(person.getTelNumber());
        if (person.isFemale()) {
            viewHolder.imageView.setBackgroundResource(R.mipmap.ic_female);
        } else {
            viewHolder.imageView.setBackgroundResource(R.mipmap.ic_male);
        }
        return convertView;
    }

    public class ViewHolder {
        ImageView imageView;
        TextView textName;
        TextView textTel;
    }
}

